package ru.fedusiv.blockchain;

import java.util.Date;
import java.util.zip.DataFormatException;

public class Block {

    public String hash;
    public String previousHash;
    private String data;
    private long timeStamp;

    public Block(String data, String previousHash) {
        this.data = data;
        this.previousHash = previousHash;
        this.timeStamp= new Date().getTime();
    }

}
