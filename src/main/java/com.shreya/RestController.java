package com.shreya;

import com.shreya.game.Card;
import com.shreya.game.Deck;
import com.shreya.game.GameService;
import com.shreya.game.GameboardAndDeck;
import com.shreya.player.PlayerData;
import com.shreya.player.PlayerService;
import com.shreya.user.LoginRequest;
import com.shreya.user.UserData;
import com.shreya.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by shreya on 25/11/16.
 */
@Controller
public class RestController {

    private static final Logger logger = LoggerFactory.getLogger(RestController.class);
    public static final String APPLICATION_JSON = "application/json";
    public static final String APPLICATION_XML = "application/xml";
    public static final String APPLICATION_HTML = "text/html";

    @Autowired private UserService userService;
    @Autowired private PlayerService playerService;
    @Autowired private GameService gameService;


    @RequestMapping(value ="/", method= RequestMethod.GET, produces=APPLICATION_JSON)
    public @ResponseBody String welcomePage(){
        return "Welcome to GameboardAndDeck game";
    }

    @RequestMapping(value="/users", method=RequestMethod.GET,produces=APPLICATION_JSON)
    public @ResponseBody List<UserData> getAllUsers(){
        List<UserData> allUsers=userService.getAllUsers();
        return allUsers;
    }

    @RequestMapping(value="/getUserByContactNumber/{contactNumber}",method=RequestMethod.GET,headers="Accept=application/json")
    public @ResponseBody UserData getUserByContactNumber(@PathVariable String contactNumber){
        UserData user1=userService.getUserByContactNumber(contactNumber);
        return user1;
    }

    @RequestMapping(value="/getUserByName/{name}",method=RequestMethod.GET,headers="Accept=application/json")
    public @ResponseBody List<UserData> getUserByName(@PathVariable String name){
        return userService.getUserByName(name);
    }

    @RequestMapping(value="/getOnlineUser",method=RequestMethod.GET,headers="Accept=application/json")
    public @ResponseBody List<UserData> getOnlineUsers(){
        return userService.getOnlineUsers();
    }

    @RequestMapping(value="/getUserFriendsList",method=RequestMethod.GET,headers="Accept=application/json")
    public @ResponseBody List<UserData> getUserFriendsList(@RequestParam("number") String number){
        return userService.getUserFriendsList(number);

    }

    @RequestMapping(value="/user",method=RequestMethod.POST,consumes={APPLICATION_JSON, APPLICATION_XML},
            produces={APPLICATION_JSON, APPLICATION_XML})
    public @ResponseBody RestResponse addUser(@RequestBody UserData user1){
                RestResponse response;

        if (!StringUtils.isEmpty(user1.getName()) &&!StringUtils.isEmpty((CharSequence) user1.getPassword())
                && (user1.getContactNumber().length())==10) {
            userService.addUser(user1);
            response = new RestResponse(true, "Successfully added user: " + user1.getContactNumber());
        } else

            response = new RestResponse(false, "invalid input");
                return response;
    }

    @RequestMapping(value="/user",method = RequestMethod.PUT,consumes={APPLICATION_JSON, APPLICATION_XML},
            produces={APPLICATION_JSON, APPLICATION_XML})
    public @ResponseBody RestResponse updateUserByContactNumber(@RequestParam("number") String number,
                                                         @RequestBody UserData user1) {
        RestResponse response;

        UserData myUser = userService.updateUser(number, user1);

        if (myUser != null) {
             response = new RestResponse(true, "Successfully updated user: " + myUser.toString());
        } else {
             response = new RestResponse(false, "Failed to update user with contact number : " + number);
        }

        return response;
    }

    @RequestMapping(value="/user",method = RequestMethod.DELETE,produces={APPLICATION_JSON, APPLICATION_XML})
    public @ResponseBody RestResponse deleteUserByContactNumber(@RequestParam("number") String number) {
        RestResponse response;

        UserData myUser= userService.deleteUser(number);

        if (myUser != null) {
           response = new RestResponse(true, "Successfully deleted user: " + myUser.toString());
        } else {
           response = new RestResponse(false, "Failed to delete user with contactNumber : " + number);
        }

        return response;
    }

    @RequestMapping(value="/User/login" , method= RequestMethod.POST, consumes={APPLICATION_JSON}, produces={APPLICATION_JSON})
    public @ResponseBody String login(@RequestBody LoginRequest subUser){
        String str=logincheck(subUser.getContactNumber());

        UserData user1= userService.getUserByContactNumber(subUser.getContactNumber());
        if((user1.getPassword()).equals(subUser.getPassword())) {
            user1.setOnline(true);
            return str + "successful login";
        }
        else
            return str+"invalid login";
    }

    @RequestMapping(value="/user/login-check",method=RequestMethod.GET )
    public @ResponseBody String logincheck(@RequestParam("Contact") String contactNumber){
        UserData user1=userService.getUserByContactNumber(contactNumber);
        if(user1==null)
            return "mobile number is not registered, ";
        else
            return "mobile number is correct, ";
    }

    @RequestMapping(value="/players", method= RequestMethod.GET,produces=APPLICATION_JSON)
    public @ResponseBody List<PlayerData> getAllPlayers(){
        List<PlayerData> allPlayers=playerService.getAllPlayers();
        return allPlayers;
    }

    @RequestMapping(value="/getPlayersByContactNumber/{contactNumber}",method=RequestMethod.GET,headers="Accept=application/json")
    public @ResponseBody List<PlayerData> getPlayersByContactNumber(@PathVariable String contactNumber){
        List<PlayerData> players=playerService.getPlayersByContactNumber(contactNumber);
        return players;
    }

    @RequestMapping(value="/getPlayerByMatchId",method = RequestMethod.GET,headers = "Accept=application/json")
    public @ResponseBody List<PlayerData> getPlayersByMatchId(@RequestParam("match_id") int match_id){
        return playerService.getPlayersByMatchId(match_id);
    }

    @RequestMapping(value="/newgame", method=RequestMethod.GET)
    public @ResponseBody String newGame(){
        return "options are:" +
                " 1)Random Opponent" +
                " 2)GameboardAndDeck Friend" +
                " 3)find opponent by name" ;
    }

    @RequestMapping(value="/newgame/findRandomOpponent", method = RequestMethod.GET)
    public @ResponseBody RestResponse findRandomOpponent(@RequestParam ("number") String number){
        RestResponse response;
        UserData user1= gameService.findRandomOpponent(number);
        if(user1==null)
            response=new RestResponse(false,"cannot find any opponent");
        else
            response=new RestResponse(true,"Random opponent is "+ user1.getContactNumber());

        return response;
    }

    @RequestMapping(value="/newgame/sequenceFriend",method = RequestMethod.GET)
    public @ResponseBody RestResponse sequenceFriend(@RequestParam ("number") String number){
        RestResponse response;
        UserData user1=gameService.sequenceFriend(number);
        if(user1==null)
            response=new RestResponse(false,"cannot find any friend");
        else
            response=new RestResponse(true,"Found GameboardAndDeck friend "+ user1.getContactNumber());

        return response;
    }

    @RequestMapping(value="/newgame/findOpponentByName",method = RequestMethod.GET)
    public @ResponseBody RestResponse findOpponentByName(@RequestParam("name") String name){
        RestResponse response;
        List<UserData> users=gameService.findOpponentByName(name);
        if(users==null)
            response=new RestResponse(false,"no player exists with name: "+name );
        else
            response=new RestResponse(true, "found players: "+users.toString());

        return response;
    }

/*
    @RequestMapping(value="/newgame/createRequest",method=RequestMethod.POST,consumes={APPLICATION_JSON}, produces={APPLICATION_JSON})
    public @ResponseBody String createRequest(@RequestParam ("number") String number){
        return "player "+number+" wants to match with you";
    }

    @RequestMapping(value="/newgame/receiveRequest",method = RequestMethod.GET)
    public @ResponseBody String getRequest(@RequestParam ("number") String number){

    }
*/
    @RequestMapping(value="/showDeck", method=RequestMethod.GET,produces=APPLICATION_JSON)
    public @ResponseBody List<Card> showDeck(@RequestParam ("match_id") int match_id){
        return gameService.showDeck(match_id);
    }

    @RequestMapping(value="/match",method=RequestMethod.GET,consumes={APPLICATION_JSON}, produces={APPLICATION_JSON})
    public @ResponseBody String match(@RequestParam("user1") UserData user1,@RequestParam("user2") UserData user2){
        gameService.match(user1,user2);
        return "Game started";
    }

    @RequestMapping(value="/makeMove",method = RequestMethod.POST,consumes={APPLICATION_JSON}, produces={APPLICATION_JSON})
    public @ResponseBody String makeMove(@RequestParam("player") PlayerData player,@RequestParam("card") Card c,@RequestParam ("match_id") int match_id,@RequestParam ("cardposition")int pos) {
        return gameService.putCardOnGameBoard(c,player,pos);
    }

    @RequestMapping(value="/deadcardReplacement",method=RequestMethod.GET)
    public @ResponseBody String deadCardReplacement(@RequestParam("player") PlayerData player,@RequestParam("card") Card card){
        return gameService.deadCardReplacement(player,card);
    }

    @RequestMapping(value="/whoseTurnToPlayNext",method = RequestMethod.GET,consumes={APPLICATION_JSON}, produces={APPLICATION_JSON})
    public @ResponseBody PlayerData whoseTurnNext(@RequestParam("match_id") int match_id){
        return gameService.whoseTurnToPlayNext(match_id);
    }

    @RequestMapping(value="/TocheckWhoWonGame",method = RequestMethod.GET,consumes={APPLICATION_JSON}, produces={APPLICATION_JSON})
    public @ResponseBody PlayerData gameWinner(@RequestParam("match_id") int match_id){
        return gameService.gameWinner(match_id);
    }
}
