package org.scq.sqs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test {

	public static void main(String[] args) {
		
		String[] array = new String[4000];
		
		for(int i=0;i< 4000; i++) {
			array[i] = String.valueOf(i);
		}
		
        int num = 100;
        int count = array.length % num == 0 ? array.length / num : array.length / num + 1;
        List<List<String>> arrayList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            int index = i * num;
            List<String> list = new ArrayList<>();
            int j = 0;
            while (j < num && index < array.length) {
                list.add(array[index++]);
                j++;
            }
            arrayList.add(list);
        }
        
        for (List<String> list : arrayList) {
            System.out.println(Arrays.toString(list.toArray()));
        }

	}

}
