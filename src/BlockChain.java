import com.google.gson.GsonBuilder;
import model.Block;

import java.util.ArrayList;


public class BlockChain {

    private static ArrayList<Block> blockchain = new ArrayList<>();
    private static int difficulty = 5;

    public static void main(String[] args) {

        blockchain.add(new Block("I am the first block", "0"));
        System.out.println("Trying to Mine block 1...");
        blockchain.get(0).mineBlock(difficulty);
        blockchain.add(new Block("I am the second block", getLastBlock().hash));
        System.out.println("Trying to Mine block 2...");
        blockchain.get(1).mineBlock(difficulty);
        blockchain.add(new Block("I am the third block", getLastBlock().hash));
        System.out.println("Trying to Mine block 3...");
        blockchain.get(2).mineBlock(difficulty);

        System.out.println("\nBlockchain is Valid: " + isChainValid());

        String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
        System.out.println(blockchainJson);
    }

    private static Block getLastBlock() {
        return blockchain.get(blockchain.size() - 1);

    }

    private static Boolean isChainValid() {
        Block currentBlock;
        Block previousBlock;
        String hashTarget = new String(new char[difficulty]).replace('\0','0');

        for (int i = 1; i < blockchain.size(); i++) {
            currentBlock = blockchain.get(i);
            previousBlock = blockchain.get(i - 1);

            if(!currentBlock.hash.equals(currentBlock.calculateHash())) {
                System.out.println("Current hashes are not equal");
                return false;
            }

            if(!previousBlock.hash.equals(currentBlock.previousHash)) {
                System.out.println("Previous Hashes are not equal");
                return false;
            }

            if(!currentBlock.hash.substring(0, difficulty).equals(hashTarget)) {
                System.out.println("This block hasn't been mined");
                return false;
            }
        }

        return true;
    }
}
