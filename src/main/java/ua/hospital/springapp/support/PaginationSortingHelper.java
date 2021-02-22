package ua.hospital.springapp.support;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * This class helps to solve issues like processing page indexes, needed to represent page hyperlink buttons in MVC's view layer. Value -1 is assigned
 * to skip some amount of pages(In view layer, they could be displayed as "...", like "| 0 |...| 4 | 5 | 6 | 7 | 8 |...| 11 |").
 * 
 * @author Alexander-PC
 *
 */

public class PaginationSortingHelper  {
	
	
			
	/**
	 * This method returns indexes, needed to represent page hyperlink buttons in MVC's view layer. Value -1 is assigned
	 * to skip some amount of pages(In view layer, they could be displayed as "...", like "| 0 |...| 4 | 5 | 6 | 7 | 8 |...| 11 |")
	 * 
	 * @param pageNumber - current slice of data from the database
	 * @param totalPages - all pages, contained in the database
	 * @return - {@link ArrayList<Integer>} of page indexes.
	 */
	public static List<Integer> calculateNumbers(int pageNumber, int totalPages) {
		List<Integer> list= new ArrayList<>();
		
		if (totalPages > 7) {
			pageNumber += 1;
			
			ArrayList<Integer> head = new ArrayList<>();
			if (pageNumber > 4) {
				head.add(1);
				head.add(-1);
			} else {
				head.add(1);
				head.add(2);
				head.add(3);
			}
			
			ArrayList<Integer> tail = new ArrayList<>();
			if (pageNumber < totalPages - 3) {
				tail.add(-1);
				tail.add(totalPages);
			} else {
				tail.add(totalPages - 2);
				tail.add(totalPages - 1);
				tail.add(totalPages);
			}
			
			ArrayList<Integer> bodyBefore = new ArrayList<>();
			if (pageNumber > 4 && pageNumber < totalPages - 1) {
				bodyBefore.add(pageNumber - 2);
				bodyBefore.add(pageNumber - 1);
			}
			
			ArrayList<Integer> bodyAfter = new ArrayList<>();
			if (pageNumber > 2 && pageNumber < totalPages - 3) {
				bodyAfter.add(pageNumber + 1);
				bodyAfter.add(pageNumber + 2);
			}
			
			list.addAll(head);
			list.addAll(bodyBefore);
			
			if (pageNumber > 3 && pageNumber < totalPages - 2) {
				list.add(pageNumber);
			}
			
			list.addAll(bodyAfter);
			list.addAll(tail);
			
		} else {
			for (int i=1; i <= totalPages; i++) {
				list.add(i);
			}
		}
	
		return list;
	}
	
}
