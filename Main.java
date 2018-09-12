/* package whatever; // don't place package name! */

import java.util.*;
import java.lang.*;
import java.io.*;

class record{
	private class Node{
		Date timestamp;
		String data;
		int nodeNumber;
		String nodeId;
		Node referenceNodeId, genesisReferenceNodeId;
        LinkedList<Node> childReferenceNodeId;
		String HashValue;
		
		Node(float data, int ownerId, String ownerName, int nodeNumber, Node referenceNodeId, Node genesisReferenceNodeId){
			this.timestamp = new Date();
			this.data = Float.toString(data) + Integer.toString(ownerId) + this.hashCode(Integer.toString(ownerId), ownerName, Float.toString(data));
			this.nodeNumber = nodeNumber;
			this.nodeId = UUID.random();
			this.referenceNodeId = referenceNodeId;
			this.childReferenceNodeId = new LinkedList<>();
			this.genesisReferenceNodeId = genesisReferenceNodeId;
			this.HashValue = this.hashCode(timestamp.toString(), data, Integer.toString(nodeNumber), nodeId, referenceNodeId.toString(), childReferenceNodeId.toString(), genesisReferenceNodeId.toString());
		}

        private String hashCode(String ...args) {
            StringBuilder builder = new StringBuilder();
			for(String val : args){
				builder.append(val);
			}
			
            return builder.toString().hashCode();
        }
	}

	private Node genesis;
	private int size;

	record(){
		this.genesis = null;
		this.size = 0;
	}

	public boolean addGenesis(float data, int ownerId, String ownerName){
		if(this.genesis != null){
			return false;
		}
		this.size = 1;
		this.genesis = new Node(data, ownerId, ownerName, this.size, null, null);

		return true;
	}

	public boolean addChild(float data, int nodeNumber, int ownerId, String ownerName){
		this.size++;
		this.addChild(this.genesis, nodeNumber, new Node(data, ownerId, ownerName, this.size, null, this.genesis));
	}

	private boolean addChild(Node node, int nodeNumber, Node child){
		if(node.nodeNumber == nodeNumber){
			child.referenceNodeId = node;
			node.childReferenceNodeId.addLast(child);
			return true;
		}

		for(Node c : node.childReferenceNodeId){
			if(this.addChild(c, child)){
				return true;
			}
		}
	}

	
}

public class Main{
	public static void main(String[] args){
		record rec = new record();
		rec.addGenesis(30, 1, "pawan");
        rec.addChild(17, 1, 1, "pawan");
	}
}