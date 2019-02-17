package com.hackdfw.serverm.networking;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.BiConsumer;
import java.util.function.BooleanSupplier;

public class TCPNetworkIO
{
	private final Map<UUID, TCPClient> _clientThreads = new ConcurrentHashMap<>();
	private final BlockingQueue<OutgoingPacket> _outgoing = new LinkedBlockingQueue<>();
	private final BiConsumer<UUID, NetworkedPacket> _consumer;
	
	public TCPNetworkIO(int port, BiConsumer<UUID, NetworkedPacket> consumer)
	{
		_consumer = consumer;
		new Thread(() ->
		{
			try (ServerSocket server = new ServerSocket(port))
			{
				while (true)
				{
					Socket socket = server.accept();
					socket.setTcpNoDelay(true);
					
					DataInputStream input = new DataInputStream(socket.getInputStream());
					DataOutputStream output = new DataOutputStream(socket.getOutputStream());
					
					
					
					TCPClient client = new TCPClient(UUID.randomUUID(), input, output, () -> socket.isClosed());
					_clientThreads.put(client.getUUID(), client);
					client.start();
				}
			}
			catch (IOException ex)
			{
				ex.printStackTrace();
			}
		}, "Main Networking Thread");
		new Thread(() ->
		{
			while (true)
			{
				try
				{
					OutgoingPacket packet = _outgoing.take();
					if (_clientThreads.containsKey(packet._target))
					{
						_clientThreads.get(packet._target).write(packet._packet);
					}
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}, "Outgoing Packets").start();
	}
	
	public void writePacket(UUID recipient, Packet packet)
	{
		try
		{
			_outgoing.put(new OutgoingPacket(packet.storeData(), recipient));
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
	private class TCPClient extends Thread
	{
		private final UUID _uuid;
		private final Object _inputLock = new Object();
		private final Object _outputLock = new Object();
		private final DataInputStream _input;
		private final DataOutputStream _output;
		private final BooleanSupplier _closeTest;
		
		private TCPClient(UUID uuid, DataInputStream input, DataOutputStream output, BooleanSupplier closeTest)
		{
			super("Client Connection Thread: " + uuid.toString());
			
			_uuid = uuid;
			synchronized (_inputLock)
			{
				_input = input;
			}
			synchronized (_outputLock)
			{
				_output = output;
			}
			_closeTest = closeTest;
		}
		
		public UUID getUUID()
		{
			return _uuid;
		}
		
		@Override
		public void run()
		{
			synchronized (_inputLock)
			{
				ByteBuffer buffer = ByteBuffer.allocate(8192);
				
				while (!_closeTest.getAsBoolean())
				{
					try
					{
						_input.readFully(buffer.array());
						
						buffer.flip();
						int length = buffer.getInt();
						if (length < buffer.remaining())
						{
							buffer.clear();
							throw new IOException("Invalid packet length");
						}
						
						byte[] bytes = new byte[length];
						buffer.get(bytes);
						
						NetworkedPacket packet = new NetworkedPacket(ByteBuffer.wrap(bytes));
						
						if (buffer.remaining() > 0)
						{
							buffer.compact();
						}
						else
						{
							buffer.clear();
						}
						
						_consumer.accept(_uuid, packet);
					}
					catch (EOFException ex)
					{
						break;
					}
					catch (IOException ex)
					{
						ex.printStackTrace();
					}
				}
			}
		}
		
		public void write(NetworkedPacket packet) throws IOException
		{
			synchronized (_outputLock)
			{
				byte[] write = packet.getData().array();
				_output.write(write);
			}
		}
	}
	
	private static class OutgoingPacket
	{
		private final NetworkedPacket _packet;
		private final UUID _target;
		
		public OutgoingPacket(NetworkedPacket packet, UUID target)
		{
			_packet = packet;
			_target = target;
		}
	}
}