package com.shreya;

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

    @Autowired
    private PlayerRepository playerRepository;


    @RequestMapping(value ="/", method= RequestMethod.GET, produces=APPLICATION_JSON)
    public @ResponseBody String welcomePage(){
        return "Welcome to Sequence Game";
    }

    @RequestMapping(value="/players", method=RequestMethod.GET,produces=APPLICATION_JSON)
    public @ResponseBody List<Player> getAllPlayers(){
        List<Player> allPlayers=playerRepository.getAllPlayers();
        return allPlayers;
    }

    @RequestMapping(value="/getPlayerByContactNumber/{contactNumber}",method=RequestMethod.GET,headers="Accept=application/json")
    public @ResponseBody Player getPlayerByContactNumber(@PathVariable String contactNumber){
        Player player1=playerRepository.getPlayerByContactNumber(contactNumber);
        return player1;
    }

    @RequestMapping(value="/getPlayerByName/{name}",method=RequestMethod.GET,headers="Accept=application/json")
    public @ResponseBody List<Player> getPlayerByName(@PathVariable String name){
        return playerRepository.getPlayerByName(name);
    }

    @RequestMapping(value="/player",method=RequestMethod.POST,consumes={APPLICATION_JSON, APPLICATION_XML},
            produces={APPLICATION_JSON, APPLICATION_XML})
    public @ResponseBody RestResponse addPlayer(@RequestBody Player player1){
                RestResponse response;
                System.out.println(player1.toString());


        if (!StringUtils.isEmpty(player1.getName()) &&!StringUtils.isEmpty((CharSequence) player1.getPassword())
                && (player1.getContactNumber().length())==10) {
            playerRepository.addPlayer(player1);
            response = new RestResponse(true, "Successfully added player: " + player1.getContactNumber());
        } else

            response = new RestResponse(false, "invalid input");
                return response;
    }

    @RequestMapping(value="/player",method = RequestMethod.PUT,consumes={APPLICATION_JSON, APPLICATION_XML},
            produces={APPLICATION_JSON, APPLICATION_XML})
    public @ResponseBody RestResponse updateBookByContactNumber(@RequestParam("number") String number,
                                                         @RequestBody Player player1) {
        RestResponse response;

        Player myPlayer = playerRepository.updatePlayer(number, player1);

        if (myPlayer != null) {
             response = new RestResponse(true, "Successfully updated player: " + myPlayer.toString());
        } else {
             response = new RestResponse(false, "Failed to update Player: with contact number : " + number);
        }

        return response;
    }

    @RequestMapping(value="/player",method = RequestMethod.DELETE,produces={APPLICATION_JSON, APPLICATION_XML})
    public @ResponseBody RestResponse deleteBookByContactNumber(@RequestParam("number") String number) {
        RestResponse response;

        Player myPlayer= playerRepository.deletePlayer(number);

        if (myPlayer != null) {
           response = new RestResponse(true, "Successfully deleted Player: " + myPlayer.toString());
        } else {
           response = new RestResponse(false, "Failed to delete Player with contactNumber : " + number);
        }

        return response;
    }

    @RequestMapping(value="/player/login" , method= RequestMethod.POST, consumes={APPLICATION_JSON}, produces={APPLICATION_JSON})
    public @ResponseBody String login(@RequestBody LoginRequest subplayer){
        String str=logincheck(subplayer.getContactNumber());

        Player player1= playerRepository.getPlayerByContactNumber(subplayer.getContactNumber());
        if((player1.getPassword()).equals(subplayer.getPassword()))
            return str+"successful login";
        else
            return str+"invalid login";
    }

    @RequestMapping(value="/player/login-check",method=RequestMethod.GET )
    public @ResponseBody String logincheck(@RequestParam("Contact") String contactNumber){
        Player player1=playerRepository.getPlayerByContactNumber(contactNumber);
        if(player1==null)
            return "mobile number is not registered, ";
        else
            return "mobile number is correct, ";
    }
}
