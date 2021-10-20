/**
 * 
 */
package com.music.apiori;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;


public class ItemSetCollection extends ArrayList<ItemSet>{
	
	// nhan vao tap cac itemset tra ve mot tap ung vien 
	public ItemSetCollection candidateC1(ItemSetCollection Li) {
		List<String> list = new ArrayList<String>();
		ItemSetCollection itemsetcollect = new ItemSetCollection();
		for (int i = 0; i < Li.size(); i++) {
			for (int j = 0; j < Li.get(i).size(); j++) {
				list.add(Li.get(i).get(j));
			}
		}
		Set<String> c1 = new TreeSet<>();
		c1.addAll(list);
		list.clear();
		list.addAll(c1);
		for(int i = 0; i < list.size(); i++) {
			ItemSet itemset = new ItemSet();
			itemset.add(list.get(i));
			itemsetcollect.add(itemset);
		}
		return itemsetcollect;
	}
}
