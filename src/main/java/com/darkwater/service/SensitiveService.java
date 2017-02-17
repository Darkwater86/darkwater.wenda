package com.darkwater.service;

import org.apache.commons.lang.CharUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo1 on 2017/2/16.
 */
@Service
public class SensitiveService implements InitializingBean {

    private static Logger logger = LoggerFactory.getLogger(SensitiveService.class);

    private TrieNode rootNode = new TrieNode();

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("SensitiveWords.txt");
            InputStreamReader reader = new InputStreamReader(is);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String lineTxt;
            while (null !=(lineTxt = bufferedReader.readLine())){
                addWord(lineTxt.trim());
            }
            reader.close();

        }catch (Exception e){
            logger.error("读取敏感词文件失败"+e.getMessage());
        }
    }

    public String filter(String text){

        if (StringUtils.isBlank(text)){
            return text;
        }

        StringBuilder result = new StringBuilder();
        String replacement = "***";
        TrieNode tempNode =rootNode;
        int begin = 0;
        int position = 0;

        while (position < text.length()){
            char c = text.charAt(position);

            //如果是符号就跳过
            if(isSymbol(c)){
                if (tempNode == rootNode){
                    ++begin;
                    result.append(c);
                }
                ++position;
                continue;
            }

            tempNode  = tempNode.getSubNode(c);

            if (null == tempNode){
                result.append(text.charAt(begin));
                position = begin+1;
                begin = position;
                tempNode = rootNode;
            }else if (tempNode.isKeyEnd()){
                //发现敏感词
                result.append(replacement);
                position = position+1;
                begin = position;
                tempNode = rootNode;
            }else {
                position = position+1;
            }
        }

        result.append(text.substring(begin));
        return result.toString();
    }
    /**
     * 增加关键词
     * @param lineText 敏感词
     * */
    private void addWord(String lineText) {
        TrieNode tempNode = rootNode;
        for (int i = 0; i < lineText.length(); ++i) {
            Character c = lineText.charAt(i);
            if(isSymbol(c)){
                continue;
            }
            TrieNode node = tempNode.getSubNode(c);
            if (null == node) {
                node = new TrieNode();
                tempNode.addSubNode(c, node);
            }
            tempNode = node;

            if ((lineText.length() - 1) == i) {
                tempNode.setKeywordEnd(true);
            }
        }
    }

    //过滤符号
    private boolean isSymbol(char c){
        int ic = (int)c;
        //东亚文字在0x2E80到0x9FFF
        return !CharUtils.isAsciiAlphanumeric(c)&&( ic < 0x2E80 || ic > 0x9FFF );
    }

    //敏感词树形结构
    private class TrieNode {
        private boolean end = false;

        private Map<Character, TrieNode> subNodes = new HashMap<>();

        public void addSubNode(Character key, TrieNode node) {
            subNodes.put(key, node);
        }

        TrieNode getSubNode(Character key) {
            return subNodes.get(key);
        }

        boolean isKeyEnd() {
            return end;
        }

        void setKeywordEnd(boolean end) {
            this.end = end;
        }
    }
//
//    public static void main(String[] args){
//        SensitiveService s = new SensitiveService();
//        s.addWord("赌 博");
//        s.addWord("外挂");
//        System.out.println(s.filter("你好赌  博啊 啊啊外  挂"));
//    }
}
