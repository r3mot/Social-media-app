package social.Controllers.Home;

import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import social.Database.Newer.Database;
import social.Objects.CurrentUser;
import social.Objects.FeedPane;
import social.Objects.Post;

public class FeedController implements Initializable {
    
    @FXML private Button createPost;
    @FXML private AnchorPane feedPane;

    private Database db;

    private int yPosition = 0;
    private boolean maxMet;
    private String content;

    private ArrayList<FeedPane> globalFeed;
    private FeedPane post;


    /**
     * 
     * @param event user created new psot
     * @throws SQLException
     * 
     * Add new post to database and local storage
     * Updates the current scene to reflect the 
     * addition of the new post
     */
    @FXML
    void post(ActionEvent event) throws SQLException {

        displayPopup();


        Post post = new Post( CurrentUser.getUsername(),  CurrentUser.getFullName(),  content,  getDate(), CurrentUser.getPictureUrl());

        db.addUserPost(post);
        globalFeed.add(new FeedPane(post));

        refreshFeed();
        
    }

    /**
     * Start of controller class
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        db = new Database();
        globalFeed = db.getGlobalFeed();
       
        initFeed();
    }

    /**
     * Displays the global feed on the dashboard
     * Updates when user submits a new post
     */
    private void initFeed(){

        yPosition = 20;

        for(int i = globalFeed.size()-1; i >= 0; i--){
            
            post = globalFeed.get(i);
            post.setLayoutY(yPosition);

            feedPane.getChildren().addAll(post);
            yPosition += post.getPaneHeight() + 20;

        }  
    }

    private void refreshFeed(){

        feedPane.getChildren().clear();
        initFeed();

    }

    /**
     * Popup for user to create new post
     * Creates a textarea for the user to provide their content
     * When the user is finished, the content is sent to the database
     * as well as local storage
     */
    private void displayPopup(){

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Create A Post");
        alert.setHeaderText("Share Your Thoughts!");

        Label maxCharsMet = new Label();
        maxCharsMet.setText("Max Characters is 120!");
        maxCharsMet.setTextFill(Color.RED);
        maxCharsMet.setVisible(false);

        TextArea textArea = new TextArea();
        textArea.setEditable(true);
        textArea.setWrapText(true);
        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        // add max character event handler
        textArea.addEventFilter(KeyEvent.KEY_TYPED, maxChars(120));

        if(maxMet){
            maxCharsMet.setVisible(true);
        }
        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(textArea, 0, 1);

        // Set expandable Exception into the dialog pane.
        alert.getDialogPane().setExpandableContent(expContent);
        alert.getDialogPane().setExpanded(true);

        Optional<ButtonType> result = alert.showAndWait();

        if(result.get() == ButtonType.OK){
            content = textArea.getText();
            return;
        }

    }

    /**
     * 
     * @param i
     * @return
     * 
     * Limits the user to 120 characters per post
     */
    private EventHandler<KeyEvent> maxChars(final Integer i){
        return new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent k){
                TextArea tx = (TextArea) k.getSource();
                if(tx.getText().length() >= i){
                    maxMet = true;
                    k.consume();
                }
            }
        };
    }

    /**
     * 
     * @return current date
     * 
     * Used for keeping track of when a post is created
     */
    private String getDate(){
        String format = "MM-dd-yyy";
        SimpleDateFormat simpledate = new SimpleDateFormat(format);
        return simpledate.format(new Date());
    }
    
}
