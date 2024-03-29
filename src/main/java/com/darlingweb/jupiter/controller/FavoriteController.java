package com.darlingweb.jupiter.controller;

import com.darlingweb.jupiter.entity.db.Item;
import com.darlingweb.jupiter.entity.request.FavoriteRequestBody;
import com.darlingweb.jupiter.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class FavoriteController {
    @Autowired
    private FavoriteService favoriteService;

    // POST
//    @RequestMapping(value = "/favorite", method = RequestMethod.POST)
//    public void setFavoriteService(@RequestBody FavoriteRequestBody requestBody,
//                                   HttpServletRequest request,
//                                   HttpServletResponse response){
//        String userId = "5678";
//        favoriteService.setFavoriteItem(userId, requestBody.getFavoriteItem());
//    }

    @RequestMapping(value = "/favorite", method = RequestMethod.POST)
    public void setFavoriteItem(@RequestBody FavoriteRequestBody requestBody,
                                HttpServletRequest request,
                                HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return ;
        }
        String userId = (String) session.getAttribute("user_id");
        favoriteService.setFavoriteItem(userId, requestBody.getFavoriteItem());
    }

    // DELETE
    @RequestMapping(value = "/favorite", method = RequestMethod.DELETE)
    public void unsetFavoriteItem(@RequestBody FavoriteRequestBody requestBody,
                                  HttpServletRequest request,
                                  HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        String userId = (String) session.getAttribute("user_id");
//        String userId = "5678";
        favoriteService.unsetFavoriteItem(userId, requestBody.getFavoriteItem().getId());
    }

    @RequestMapping(value = "/favorite", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, List<Item>> getFavoriteItem(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return new HashMap<>();
        }

        String userId = (String) session.getAttribute("user_id");
//        String userId = "5678";
        return favoriteService.getFavoriteItems(userId);
    }


}
