/**
 * 
 */
package com.music.apiori;

import java.util.ArrayList;
import java.util.List;


public class AssociationRule {
	private ItemSet x;
	private ItemSet y;
	private double support;
	private double confidence;

	public AssociationRule() {
		
	}

	public ItemSet getX() {
		return x;
	}

	public void setX(ItemSet x) {
		this.x = x;
	}

	public ItemSet getY() {
		return y;
	}

	public void setY(ItemSet y) {
		this.y = y;
	}

	public double getSupport() {
		return support;
	}

	public void setSupport(double support) {
		this.support = support;
	}

	public double getConfidence() {
		return confidence;
	}

	public void setConfidence(double confidence) {
		this.confidence = confidence;
	}

	public AssociationRule(ItemSet x, ItemSet y, double support,
			double confidence) {
		this.x = x;
		this.y = y;
		this.support = support;
		this.confidence = confidence;
	}

public String toString(AssociationRule rule) {
	
	return rule.getX()+" ==>"+rule.getY() +" confidence: "+rule.confidence;
}
//Tim tap pho bien
	public ItemSetCollection FindingLargeItemset(ItemSetCollection D,
			double supportThreshold) {
		ItemSetCollection c = new ItemSetCollection();
		ItemSetCollection li = new ItemSetCollection();
		ItemSetCollection l = new ItemSetCollection();
		c = new ItemSetCollection().candidateC1(D);// gan tap ung vien C1
		// k la so buoc phat sinh tap ug vien
		int k = 2;
		while (!c.isEmpty()) {
			li.clear();
			for (ItemSet itset : c) {
				if (support(itset, D) >= supportThreshold) {
					li.add(itset);
				}
			}
			l.addAll(li);// add tap ung vien Vax l
			c.clear();// khoi tao lai Ck;
			c.addAll(findSubSet(li, k));//tap ung vien phat sinh tu li;
			k++;
		}
		return l;

	}

	public boolean tiaCk(ItemSet c, ItemSetCollection li) {
//		System.out.println(li);
//		System.out.println(c);
		int count =0;
		for (int i = 0; i <c.size()-1; i++) {
			ItemSet tmp = new ItemSet();
			tmp.addAll(c);
			tmp.remove(i);
			for (int j = 0; j < li.size(); j++) {
				if(tmp.containsAll(li.get(j))){
					count++;
				}
			}
		}
		return count==c.size()-1;
	}

	//Luat ket hop nhan vao db, cac item pho bien l, ThordConfidence; tra ve danh sach cac luat ket hop
	public List<AssociationRule> assRule(ItemSetCollection db,
			ItemSetCollection freItem, double minConf) {
		List<AssociationRule> allRule = new ArrayList<AssociationRule>();// Khoi tao mot danh sach cac luat ket hop
                
                int max=0;
                for(ItemSet item:freItem){
                    max=item.size();
                }
                
                ItemSetCollection freItemSet=new ItemSetCollection();
                for(ItemSet item:freItem){
                    if(item.size()==max){
                        freItemSet.add(item);
                    }
                }
		for (int i = 0; i < freItemSet.size(); i++) {
			if (freItemSet.get(i).size() > 1) {
				ItemSetCollection it = new ItemSetCollection();
                                // gia tri tap cac itemset con  tu 1 itemset trong tap PB l
				it.addAll(subItemSet(freItemSet.get(i)));
				double xy = support(freItemSet.get(i), db);
				for (int j = 0; j <it.size(); j++) {
					double x=support(it.get(j), db);
					double confidence = (double)(xy/x)*100;
					ItemSet its = new ItemSet();
					its.addAll(freItemSet.get(i));
					if(confidence>=minConf){
						its.removeAll(it.get(j));
						allRule.add(new AssociationRule((it.get(j)),its, x, confidence));
					}
				}
				
			}
		}
		return allRule;
	}

	// nhan vao mot itemset pho bien tra ve tap cac itemset con
	public ItemSetCollection subItemSet(ItemSet itemSet) {
		ItemSetCollection itc = new ItemSetCollection();
		ItemSetCollection tmp = new ItemSetCollection();
		ItemSetCollection tmp1 = new ItemSetCollection();
		for (int i = 0; i < itemSet.size(); i++) {
			ItemSet its = new ItemSet();
			its.add(itemSet.get(i));
			itc.add(its);
			tmp.add(its);
		}
		for (int i = 1; i < itemSet.size() - 1; i++) {
			itc.addAll(subItem(tmp, itemSet));
			tmp1.addAll(subItem(tmp, itemSet));
			tmp.clear();
			tmp.addAll(tmp1);
			tmp1.clear();
		}
		return itc;
	}

	// nhan vao mot tap cac itemset tao ra item con
	public ItemSetCollection subItem(ItemSetCollection tmp, ItemSet itemSet) {
		ItemSetCollection itcl = new ItemSetCollection();
		for (int i = 0; i < tmp.size(); i++) {
			int count = 0;
			for (int j = 0; j < itemSet.size() - 1; j++) {
				ItemSet it = new ItemSet();
				it.addAll(tmp.get(i));
				if (count == 1) {
					it.add(itemSet.get(j + 1));
					itcl.add(it);
				}
				if (it.get(it.size() - 1).equals(itemSet.get(j))) {
					it.add(itemSet.get(j + 1));
					itcl.add(it);
					count++;
				}
			}
		}
		return itcl;
	}

	//phat sinh tap cac itemset tu li;
	public ItemSetCollection findSubSet(ItemSetCollection li, int k) {
		ItemSetCollection itcol = new ItemSetCollection();
		List<String> list = new ArrayList<>();
		if (k == 2) {
			for (int i = 0; i < li.size(); i++) {
				for (int j = 0; j < li.get(i).size(); j++) {
					list.add(li.get(i).get(j));
				}
			}
			for (int j = 0; j < list.size(); j++) {
				for (int i = j + 1; i < list.size(); i++) {
					ItemSet it = new ItemSet();
					it.add(list.get(j));
					it.add(list.get(i));
					itcol.add(it);
				}
			}
		} else {
			ItemSetCollection itcol1 = new ItemSetCollection();
			itcol1.addAll(li);
			for (int i = 0; i < li.size(); i++) {
				for (int j = i + 1; j < itcol1.size(); j++) {
					if (!cadC(li.get(i), itcol1.get(j)).isEmpty()) {
						ItemSet its = new ItemSet();
						if(tiaCk(cadC(li.get(i), itcol1.get(j)),li)==true);{
						itcol.add(cadC(li.get(i), itcol1.get(j)));
					}}
				}
			}
		}
		return itcol;
	}
// Ket hop 2 itemset co n-1 item giong nhau
	private ItemSet cadC(ItemSet item1, ItemSet item2) {
		int count = 0;
		ItemSet it = new ItemSet();
		for (int i = 0; i < item1.size() - 1; i++) {
			if (item1.get(i).equals(item2.get(i))) {
				count++;
			}
		}
		if (count == item1.size() - 1) {
			it.addAll(item1);
			it.add(item2.get(item2.size() - 1));
		}
		return it;
	}

	//Tinh do ho tro cua 1 itemSet
	public double support(ItemSet itset, ItemSetCollection d) {
		double count = 0;
		for (ItemSet itemSet : d) {
			if (itemSet.containsAll(itset)) {
				count++;
			}
		}
		return (double) count / d.size();
	}

}

