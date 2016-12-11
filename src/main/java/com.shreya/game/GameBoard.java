package com.shreya.game;

import com.shreya.player.PlayerData;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by shreya on 29/11/16.
 */
@Data
public class GameBoard {
    private HashMap<Integer, Card> gameboard;
    private HashMap<Integer, Character> fill;

    public GameBoard() {
        gameboard=new HashMap<Integer, Card>();
        gameboard.put(1, new Card('s', 2));
        gameboard.put(2, new Card('s', 3));
        gameboard.put(3, new Card('s', 4));
        gameboard.put(4, new Card('s', 5));
        gameboard.put(5, new Card('s', 6));
        gameboard.put(6, new Card('s', 7));
        gameboard.put(7, new Card('s', 8));
        gameboard.put(8, new Card('s', 9));
        gameboard.put(9, new Card('d', 1));
        gameboard.put(10, new Card('c', 1));
        gameboard.put(11, new Card('c', 13));
        gameboard.put(12, new Card('c', 12));
        gameboard.put(13, new Card('c', 10));
        gameboard.put(14, new Card('c', 9));
        gameboard.put(15, new Card('c', 8));
        gameboard.put(16, new Card('s', 10));
        gameboard.put(17, new Card('d', 13));
        gameboard.put(18, new Card('h', 10));
        gameboard.put(19, new Card('h', 9));
        gameboard.put(20, new Card('h', 8));
        gameboard.put(21, new Card('h', 7));
        gameboard.put(22, new Card('h', 6));
        gameboard.put(23, new Card('c', 7));
        gameboard.put(24, new Card('s', 12));
        gameboard.put(25, new Card('d', 12));
        gameboard.put(26, new Card('h', 12));
        gameboard.put(27, new Card('h', 2));
        gameboard.put(28, new Card('h', 3));
        gameboard.put(29, new Card('h', 4));
        gameboard.put(30, new Card('h', 5));
        gameboard.put(31, new Card('c', 6));
        gameboard.put(32, new Card('s', 13));
        gameboard.put(33, new Card('d', 10));
        gameboard.put(34, new Card('h', 13));
        gameboard.put(35, new Card('h', 1));
        gameboard.put(36, new Card('c', 2));
        gameboard.put(37, new Card('c', 3));
        gameboard.put(38, new Card('c', 4));
        gameboard.put(39, new Card('c', 5));
        gameboard.put(40, new Card('s', 1));
        gameboard.put(41, new Card('d', 9));
        gameboard.put(42, new Card('d', 8));
        gameboard.put(43, new Card('d', 7));
        gameboard.put(44, new Card('d', 6));
        gameboard.put(45, new Card('d', 5));
        gameboard.put(46, new Card('d', 4));
        gameboard.put(47, new Card('d', 3));
        gameboard.put(48, new Card('d', 2));
        fill = new HashMap<Integer, Character>();
        for(int i=1;i<49;i++)
            fill.put(i,'0');
    }


    public void displayBoard() {
        int counter = 1;
        for (int i = 1; i <= 6; i++) {
            for (int j = 1; j <= 8; j++) {
                System.out.print(gameboard.get(counter).toString() + "/t");
                counter++;
            }
            System.out.println();
        }
    }
}