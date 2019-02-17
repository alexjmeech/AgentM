package com.hackdfw.clientm;

import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.ArrayList;

@SuppressWarnings("Duplicates")
public class App extends Application {
    private Stage stage;
    private static int GAMEMODE = 0;
    private StackPane root;
    private Scene scene;
    private Player client;
    private ArrayList<Player> players = new ArrayList<>();



    @Override
    public void start(Stage stage) {
        this.stage = stage;
        stageSetup(this.stage);
    }

    private void nameInputPane() {
        GridPane prompt = new GridPane();
        prompt.setMaxWidth(500);
        prompt.setMaxHeight(300);
        prompt.setStyle("-fx-background-color: rgba(255, 255, 255, 0.79);" +
                "-fx-padding: 50px;" +
                "-fx-background-radius: 10px;");
        prompt.setAlignment(Pos.CENTER);
        prompt.setVgap(10);

        GridPane bottom = new GridPane();
        bottom.setAlignment(Pos.BOTTOM_CENTER);
        bottom.setStyle("-fx-background-color: transparent;" +
                "-fx-padding: 50px;" +
                "-fx-background-radius: 10px;");
        bottom.setHgap(5);


        TextField name = new TextField();
        name.setPromptText("Enter character name");
        name.setPrefColumnCount(20);

        Button play = new Button("Play");
        play.setOnAction(e -> {
            client = new Player(name.getText(), false, "Lawyer");
            players.add(new Player("Range105", true, "FBI Agent"));
            players.add(new Player("waterBottle", false, "Spy"));
            players.add(client);
            GAMEMODE = 1;
            changeBackground();
        });

        bottom.add(name, 0, 0);
        bottom.add(play, 1, 0);

        GridPane top = new GridPane();
        top.setAlignment(Pos.TOP_CENTER);
        top.setStyle("-fx-background-color: transparent;" +
                "-fx-padding: 50px;" +
                "-fx-background-radius: 10px;");
        top.setMinHeight(100);
        top.setMinWidth(100);

        Label banner = new Label();
        banner.setFocusTraversable(false);
        banner.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.ITALIC, 20));
        banner.setText("Sleeper Agent Force: M");
        top.add(banner, 0, 0);

        prompt.add(top, 0, 0);
        prompt.add(bottom, 0, 1);

        root.getChildren().add(prompt);
    }

    private void gameScreen() {
        StackPane stack = new StackPane();
        stack.setStyle("-fx-background-color: transparent;" +
                "-fx-padding: 10px");

        GridPane lgrid = new GridPane();
        lgrid.maxHeight(stage.getMaxHeight());
        lgrid.maxWidth(stage.getMaxWidth());
        lgrid.setHgap(10);
        lgrid.setVgap(10);
        lgrid.add(mainNameNode(client), 0, 0);

        GridPane lsGrid = new GridPane();
        lsGrid.setAlignment(Pos.TOP_LEFT);
        lsGrid.setHgap(10);
        lsGrid.setVgap(10);
        lsGrid.add(graveyardNode(), 0, 0);
        lsGrid.add(roleNode(), 1, 0);

        lgrid.add(lsGrid, 0, 1);
        lgrid.add(chatNode(), 0, 2);

        GridPane rgrid = new GridPane();
        rgrid.maxHeight(stage.getMaxHeight());
        rgrid.maxWidth(stage.getMaxWidth());
        rgrid.setHgap(10);
        rgrid.setVgap(10);
        rgrid.setAlignment(Pos.TOP_RIGHT);

        Label placeholder = new Label();
        placeholder.setVisible(false);
        rgrid.add(placeholder, 0, 0);
        rgrid.add(classNode(), 0, 1);
        rgrid.add(actionNode(), 0, 2);



        stack.getChildren().add(lgrid);
        stack.getChildren().add(rgrid);
        root.getChildren().add(stack);
    }

    private Node mainNameNode(Player player) {
        Label nameDisplay = new Label();
        nameDisplay.setFocusTraversable(false);
        nameDisplay.setStyle("-fx-background-color: white;" +
                "-fx-padding: 5px;" +
                "-fx-background-radius: 10px");
        nameDisplay.setText(player.getInstanceName());
        return nameDisplay;
    }

    private Node nameNode(Player player) {
        Label nameDisplay = new Label();
        nameDisplay.setFocusTraversable(false);
        nameDisplay.setStyle("-fx-background-color: transparent;" +
                "-fx-padding: 5px;");
        nameDisplay.setText(player.getInstanceName());
        return nameDisplay;
    }

    private Node roleNode(Player player) {
        Label nameDisplay = new Label();
        nameDisplay.setFocusTraversable(false);
        nameDisplay.setStyle("-fx-background-color: transparent;" +
                "-fx-padding: 5px");
        nameDisplay.setText(player.getInstanceClass());

        return nameDisplay;
    }

    private Node deadNode(Player player) {
        Label nameDisplay = new Label();
        nameDisplay.setFocusTraversable(false);
        nameDisplay.setStyle("-fx-background-color: transparent;" +
                "-fx-padding: 5px");
        nameDisplay.setText(player.getInstanceName() + " (" + player.getInstanceClass() + ")");
        return nameDisplay;
    }

    private Node graveyardNode() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setStyle("-fx-background-color: rgba(255, 255, 255, 0.79);" +
                "-fx-padding: 5px;" +
                "-fx-background-radius: 10px;");
        grid.setMinHeight(200);
        grid.setMaxHeight(500);
        grid.setMaxWidth(300);

        Label title = new Label();
        title.setMaxHeight(20);
        title.setMaxWidth(150);
        title.setFocusTraversable(false);
        title.setText("Graveyard");

        grid.add(title, 0, 0);
        Separator separator = new Separator();
        separator.setOrientation(Orientation.HORIZONTAL);
        grid.add(separator, 0, 1);

        for(int i = 0 ; i < players.size(); i++) {
            if(players.get(i).isDead()) {
                grid.add(deadNode(players.get(i)), 0, i+2);
            }
        }

        return grid;
    }

    private Node roleNode() {
        GridPane grid = new GridPane();
        grid.setMinHeight(200);
        grid.setMaxHeight(500);
        grid.setMaxWidth(300);
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setStyle("-fx-background-color: rgba(255, 255, 255, 0.79);" +
                "-fx-padding: 5px;" +
                "-fx-background-radius: 10px;");

        Label title = new Label();
        title.setMaxHeight(20);
        title.setMaxWidth(150);
        title.setFocusTraversable(false);
        title.setText("Role List");

        grid.add(title, 0, 0);
        Separator separator = new Separator();
        separator.setOrientation(Orientation.HORIZONTAL);
        grid.add(separator, 0, 1);

        for(int i = 0; i < players.size(); i++) {
            grid.add(roleNode(players.get(i)), 0, i+2);
        }
        return grid;
    }

    private Node chatNode() {
        GridPane grid = new GridPane();
        grid.setMinHeight(400);
        grid.setMaxHeight(500);
        grid.setAlignment(Pos.BOTTOM_CENTER);
        grid.setStyle("-fx-background-color: rgba(255, 255, 255, 0.79);" +
                "-fx-padding: 5px;" +
                "-fx-background-radius: 10px;");
        grid.setVgap(5);
        grid.setHgap(5);

        TextArea chat = new TextArea();
        chat.setMinHeight(360);
        chat.setMaxHeight(360);
        chat.setStyle("-fx-background-color: rgba(255, 255, 255, 0.79);" +
                "text-area-background: rgba(255, 255, 255, 0.79);" +
                "-fx-padding: 5px;" +
                "-fx-background-radius: 10px;");

        TextField message = new TextField();
        message.setMinHeight(20);
        message.setMaxHeight(20);
        grid.add(chat, 0, 0);
        grid.add(message, 0, 1);
        return grid;
    }

    private Node classNode() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setStyle("-fx-background-color: rgba(255, 255, 255, 0.79);" +
                "-fx-padding: 5px;" +
                "-fx-background-radius: 10px;");
        grid.setMinHeight(200);
        grid.setMaxHeight(500);
        grid.minWidth(200);
        grid.maxWidth(250);

        Label title = new Label();
        title.setMaxHeight(20);
        title.setMaxWidth(150);
        title.setFocusTraversable(false);
        title.setText(client.getInstanceClass());

        grid.add(title, 0, 0);
        Separator separator = new Separator();
        separator.setOrientation(Orientation.HORIZONTAL);
        grid.add(separator, 0, 1);
        return grid;
    }

    private Node actionNode() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setStyle("-fx-background-color: rgba(255, 255, 255, 0.79);" +
                "-fx-padding: 5px;" +
                "-fx-background-radius: 10px;");
        grid.setMinHeight(200);
        grid.setMaxHeight(500);
        grid.minWidth(150);
        grid.maxWidth(250);
        grid.setHgap(25);

        for(int i = 0; i < players.size(); i++) {
            if(!players.get(i).isDead()) {
                grid.add(nameNode(players.get(i)), 0, i);

                Button play = new Button("PH");
                play.setOnAction(e -> {
                    System.out.println("Hello!");
                });
                grid.add(play, 1, i);
            }
        }

        return grid;
    }

    private void stageSetup(Stage stage) {
        stage.setX(0);
        stage.setY(0);
        stage.setMinHeight(720);
        stage.setMinWidth(1280);
        stage.setMaxHeight(720);
        stage.setMaxWidth(1280);
        stage.setResizable(false);

        stage.setTitle("Sleeper Agent Force: M");
        stage.getIcons().add(new Image("logo.png"));

        root = new StackPane();
        root.setPrefSize(stage.getWidth(), stage.getHeight());
        changeBackground();

        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void changeBackground() {
        root.getChildren().clear();

        Image img;

        switch(GAMEMODE) {
            case 1: {
                img = new Image("play.png");
                gameScreen();
                break;
            }


            default: {
                img = new Image("menu.png");
                nameInputPane();
            }
        }

        BackgroundImage bg = new BackgroundImage(img,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100,
                        true, true, true, true));
        root.setBackground(new Background(bg));
    }
}
