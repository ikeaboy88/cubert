package com.pc;

import java.util.Comparator;

public class NodeComparator implements Comparator<Node> {

	@Override
	public int compare(Node first_node, Node second_node) {
		
		if (first_node.getTotalCosts() < second_node.getTotalCosts()) {
			return -1;
		} else if (first_node.getTotalCosts() > second_node.getTotalCosts()) {
			return 1;
		} else {
			return 0;
		}
	}
}
