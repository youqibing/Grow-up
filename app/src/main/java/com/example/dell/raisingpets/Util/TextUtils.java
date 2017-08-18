package com.example.dell.raisingpets.Util;

import java.util.Random;

/**
 * Created by root on 16-12-1.
 */

public class TextUtils {

    public static String AiYaXiYaDialog(){
        String[] dialog = new String[]{
                "诶？你看得见我？",
                "刚刚路过的村民被我吓了一跳，感觉自己被人讨厌了呢",
                "我也不记得自己是从哪儿来的了",
                "哈哈，我的身体能穿过树",
                "这条路的尽头是什么呢……",
                "好想养一只猫陪我玩啊",
                "你看我的身体是透明的",
                "为什么我会在这里呢，好奇怪",
                "我好像不属于这个世界，可是我该去哪里呢……",
                "你不开心吗？",
                "我刚刚在做什么来着，不记得了",
                "啦～啦～啦～",
                "身体轻飘飘的，我会不会被吹走呢"
        };

        Random rand = new Random();
        int i = rand.nextInt(12);

        return dialog[i];
    }
}
