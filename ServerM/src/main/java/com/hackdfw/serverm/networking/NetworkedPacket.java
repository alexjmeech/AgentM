package com.hackdfw.serverm.networking;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * A Packet is a container of any type of data.
 */
public class NetworkedPacket
{
	private final Object _lock = new Object();
	private ByteBuffer _data;

	/**
	 * Initializes this object.
	 * @param messages A variable number of data to be written to this Packet.
	 */
	public NetworkedPacket()
	{
		this(ByteBuffer.allocate(64));
	}

	NetworkedPacket(ByteBuffer buffer)
	{
		synchronized (_lock)
		{
			_data = buffer;
		}
	}

	ByteBuffer getData()
	{
		synchronized (_lock)
		{
			return _data;
		}
	}

	/**
	 * Reads the next byte.
	 * @return The next byte.
	 */
	public byte readByte()
	{
		synchronized (_lock)
		{
			return _data.get();
		}
	}

	/**
	 * Reads the next short.
	 * @return The next short.
	 */
	public short readShort()
	{
		synchronized (_lock)
		{
			return _data.getShort();
		}
	}

	/**
	 * Reads the next char.
	 * @return The next char.
	 */
	public char readChar()
	{
		synchronized (_lock)
		{
			return _data.getChar();
		}
	}

	/**
	 * Reads the next integer.
	 * @return The next integer.
	 */
	public int readInt()
	{
		synchronized (_lock)
		{
			return _data.getInt();
		}
	}

	/**
	 * Reads the next long.
	 * @return The next long.
	 */
	public long readLong()
	{
		synchronized (_lock)
		{
			return _data.getLong();
		}
	}

	/**
	 * Reads the next float.
	 * @return The next float.
	 */
	public float readFloat()
	{
		synchronized (_lock)
		{
			return _data.getFloat();
		}
	}

	/**
	 * Reads the next double.
	 * @return The next double.
	 */
	public double readDouble()
	{
		synchronized (_lock)
		{
			return _data.getDouble();
		}
	}

	/**
	 * Reads the next boolean.
	 * @return The next boolean.
	 */
	public boolean readBoolean()
	{
		synchronized (_lock)
		{
			return readByte() == (byte)1;
		}
	}

	/**
	 * Reads the next String.
	 * @return The next String.
	 */
	public String readString()
	{
		synchronized (_lock)
		{
			byte[] b = new byte[readInt()];

			_data.get(b);

			return new String(b, Charset.defaultCharset());
		}
	}

	/**
	 * Writes a byte.
	 * @param b The byte.
	 * @return Returns this.
	 */
	public NetworkedPacket writeByte(byte... b)
	{
		synchronized (_lock)
		{
			ensureSize(b.length);

			_data.put(b);

			return this;
		}
	}

	/**
	 * Writes a short.
	 * @param s The short.
	 * @return Returns this.
	 */
	public NetworkedPacket writeShort(short... ss)
	{
		synchronized (_lock)
		{
			ensureSize(ss.length * 2);

			for (short s : ss)
			{
				_data.putShort(s);
			}

			return this;
		}
	}

	/**
	 * Writes a char.
	 * @param c The char.
	 * @return Returns this.
	 */
	public NetworkedPacket writeChar(char... cs)
	{
		synchronized (_lock)
		{
			ensureSize(cs.length * 2);

			for (char c : cs)
			{
				_data.putChar(c);
			}

			return this;
		}
	}

	/**
	 * Writes an integer.
	 * @param i The integer.
	 * @return Returns this.
	 */
	public NetworkedPacket writeInt(int... is)
	{
		synchronized (_lock)
		{
			ensureSize(is.length * 4);

			for (int i : is)
			{
				_data.putInt(i);
			}

			return this;
		}
	}

	/**
	 * Writes a long.
	 * @param l The long.
	 * @return Returns this.
	 */
	public NetworkedPacket writeLong(long... ls)
	{
		synchronized (_lock)
		{
			ensureSize(ls.length * 8);

			for (long l : ls)
			{
				_data.putLong(l);
			}

			return this;
		}
	}

	/**
	 * Writes a float.
	 * @param f The float.
	 * @return Returns this.
	 */
	public NetworkedPacket writeFloat(float... fs)
	{
		synchronized (_lock)
		{
			ensureSize(fs.length * 4);

			for (float f : fs)
			{
				_data.putFloat(f);
			}

			return this;
		}
	}

	/**
	 * Writes a double.
	 * @param d The double.
	 * @return Returns this.
	 */
	public NetworkedPacket writeDouble(double... ds)
	{
		synchronized (_lock)
		{
			ensureSize(ds.length * 8);

			for (double d : ds)
			{
				_data.putDouble(d);
			}

			return this;
		}
	}

	/**
	 * Writes a boolean.
	 * @param b The boolean.
	 * @return Returns this.
	 */
	public NetworkedPacket writeBoolean(boolean... bs)
	{
		synchronized (_lock)
		{
			ensureSize(bs.length);

			for (boolean b : bs)
			{
				_data.put(b ? (byte)1 : (byte)0);
			}

			return this;
		}
	}

	/**
	 * Writes a String.
	 * @param s The String.
	 * @return Returns this.
	 */
	public NetworkedPacket writeString(String... ss)
	{
		synchronized (_lock)
		{
			for (String s : ss)
			{
				byte[] b = s.getBytes(Charset.defaultCharset());

				ensureSize(b.length + 4);

				_data.putInt(b.length);

				_data.put(b);
			}

			return this;
		}
	}

	/**
	 * Writes a Packet.
	 * @param p The Packet.
	 * @return Returns this.
	 */
	public NetworkedPacket writePacket(NetworkedPacket p)
	{
		synchronized (_lock)
		{
			_data.put(p._data);
			return this;
		}
	}

	/**
	 * Absolute get method
	 * @param index The index of the object
	 * @return The object at the specified index.
	 */
	public byte get(int index)
	{
		synchronized (_lock)
		{
			return _data.get(index);
		}
	}

	/**
	 * Absolute set method
	 * @param index The index of the object
	 * @param o The object to set 
	 * @return Returns this.
	 */
	public NetworkedPacket set(int index, byte b)
	{
		synchronized (_lock)
		{
			_data.put(index, b);
			return this;
		}
	}

	public int getPosition()
	{
		synchronized (_lock)
		{
			return _data.position();
		}
	}

	public void setPosition(int pos)
	{
		synchronized (_lock)
		{
			_data.position(pos);
		}
	}

	/**
	 * Resets the position but does not clear the buffer.
	 * @return Returns this.
	 */
	public NetworkedPacket reset()
	{
		synchronized (_lock)
		{
			_data.rewind();
			return this;
		}
	}

	/**
	 * Clears the entire buffer and resets the Packet.
	 * @return Returns this.
	 */
	public NetworkedPacket clear()
	{
		synchronized (_lock)
		{
			_data.clear();
			return this;
		}
	}

	public int getRemaining()
	{
		synchronized (_lock)
		{
			return _data.remaining();
		}
	}

	public boolean hasMore()
	{
		return getRemaining() > 0;
	}

	private void ensureSize(int size)
	{
		synchronized (_lock)
		{
			if (_data.capacity() >= _data.position() + size)
			{
				return;
			}
			ByteBuffer temp = ByteBuffer.allocate((_data.position() + size + 1) * 3 / 2);
			_data.flip();
			temp.put(_data);
			_data = temp;
		}
	}
}