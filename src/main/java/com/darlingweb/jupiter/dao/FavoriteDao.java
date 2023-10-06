package com.darlingweb.jupiter.dao;

import com.darlingweb.jupiter.entity.db.Item;
//import com.darlingweb.jupiter.entity.db.User;
//import com.mysql.cj.Session;
import com.darlingweb.jupiter.entity.db.ItemType;
import com.darlingweb.jupiter.entity.db.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class FavoriteDao {
    @Autowired  // 表明这是一个bean，从library中来的class
    private SessionFactory sessionFactory;

    // Insert a favorite record to the database
    public void setFavoriteItem(String userId, Item item) {
        Session session = null;

        try {
            // 打开db进行操作
            session = sessionFactory.openSession();
            //SELECT * FROM user WHERE user_id = '123'
            User user = session.get(User.class, userId);

            user.getItemSet().add(item);
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            if (session != null) session.close();
        }
    }

    // Remove a favorite record from the database
    public void unsetFavoriteItem(String userId, String itemId) {
        Session session = null;

        try {
            session = sessionFactory.openSession();
            User user = session.get(User.class, userId);
            Item item = session.get(Item.class, itemId);
            user.getItemSet().remove(item);
            session.beginTransaction();
            session.update(user);
            session.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            if (session != null) session.close();
        }
    }

    public Set<Item> getFavoriteItems(String userId) {
        Session session = null;
        try {
            session = sessionFactory.openSession();// 更简便写法，与分开写无异（打开session）
            return session.get(User.class, userId).getItemSet();
        } catch (Exception ex) {
            ex.printStackTrace();
            return new HashSet<>();
        } finally {
            if (session != null) session.close();
        }

    }

    // Get favorite item ids for the given user
    public Set<String> getFavoriteItemIds(String userId) {
        Set<String> itemIds = new HashSet<>();

        try (Session session = sessionFactory.openSession()) {
            Set<Item> items = session.get(User.class, userId).getItemSet();
            for(Item item : items) {
                itemIds.add(item.getId());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return itemIds;
    }

    // Get favorite items for the given user.
    // The returned map includes three entries like
    // {"Video": [item1, item2, item3], "Stream": [item4, item5, item6], "Clip": [item7, item8, ...]}
    public Map<String, List<String>> getFavoriteGameIds(Set<String> favoriteItemIds){
        Map<String, List<String>> itemMap = new HashMap<>();
        for (ItemType type : ItemType.values()){
            itemMap.put(type.toString(), new ArrayList<>());
        }

        try (Session session = sessionFactory.openSession()) {
            for (String itemId: favoriteItemIds){
                Item item = session.get(Item.class, itemId);
                itemMap.get(item.getType().toString()).add(item.getGameId());
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return itemMap;
    }


//    public Map<String, List<String>> getFavoriteGameIds(Set<Item> items){
//        Map<String, List<String>> itemMap = new HashMap<>();
//        for (ItemType type: ItemType.values()){
//            itemMap.get(item.getType().toString()).add(item.getGameId());
//
//
//        }







}

