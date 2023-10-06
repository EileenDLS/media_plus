package com.darlingweb.jupiter.controller;

import com.darlingweb.jupiter.service.GameService;
import com.darlingweb.jupiter.service.TwitchException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class GameController {

//    // no dependency injection: 自己创建一个实力话对象
//    private GameService gameService = new GameService();

    // dependency injection: field injection
    @Autowired
    private  GameService gameService;

//    @RequestMapping(value = "/game", method = RequestMethod.GET)
//    public void getGame(@RequestParam(value = "game_name", required = false) String gameName, HttpServletResponse response) throws IOException {
////        System.out.println(gameName);
//
////        String fakeName = gameService.fakeName();
////        response.getWriter().write(fakeName);
//    }

//    @RequestMapping(value = "/search", method = RequestMethod.GET)
//    @ResponseBody
//    public String search(@RequestParam("lon") double lon, @RequestParam("lat") double lat) {
//        return "HELLO! lon: " + lon + " lat: " + lat;
//    }


    @RequestMapping(value = "/game", method = RequestMethod.GET)
    public void getGame(@RequestParam(value = "game_name", required = false) String gameName, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        try {
            // Return the dedicated game information if gameName is provided in the request URL, otherwise return the top x games.
            if (gameName != null) {
                response.getWriter().print(new ObjectMapper().writeValueAsString(gameService.searchGame(gameName)));
            } else {
                response.getWriter().print(new ObjectMapper().writeValueAsString(gameService.topGames(0)));
            }
        } catch (TwitchException e) {
            throw new ServletException(e);
        }
    }


}
