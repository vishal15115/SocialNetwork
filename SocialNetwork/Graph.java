/*
 * VISHAL RAJ DUTTA  2015115
 * SAURABH KAPUR     2015087
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Graph {
static ArrayList<User> ad_list = new ArrayList<User>();


public void build() throws FileNotFoundException, IOException
{
	
		
		
		try(BufferedReader br = new BufferedReader(new FileReader("/Users/saurabhkapur/Documents/workspace/Social_network/src/input.txt"))) {
			for(String line; (line = br.readLine()) != null; )
			{
				String []a = line.split(",");
				
				String friends[] = new String [100];
	    		int no = Integer.parseInt(a[3]);
	    		if (no != 0)
	    		{
	    			for (int i=0 ;i<no ;i++)
	    			{
	    				friends[i] = a[4+i];
	    			}
	    		}
	    		String pending[] = new String [100];
	    		int no_pending = Integer.parseInt(a[4+no]);
	    		if (no_pending != 0)
	    		{
	    			for (int i=0 ;i<no_pending ;i++)
	    			{
	    				pending[i] = a[5+no+i];
	    			}
	    		}
	    		User user = new User (a[0],a[1],a[2],no,friends,no_pending,pending,a[4+no+no_pending+1]);
	    		ad_list.add(user);
			}
			
			User current;
			for(int k=0;k<ad_list.size();k++)
			{
				current=ad_list.get(k);
				for(int i=0;i<current.no_friends;i++)
				{
					for(int j=0;j<ad_list.size();j++)
					{
						ad_list.get(j).unvisited();
						ad_list.get(j).change_value(Integer.MAX_VALUE);
						if(ad_list.get(j).getUsername().equals(current.getFriend(i)))
						{
							current.friends_array.add(ad_list.get(j));
							break;
						}
					}		
				}			
				for(int i=0;i<current.no_pending_req;i++)
				{
					for(int j=0;j<ad_list.size();j++)
					{
						if(ad_list.get(j).getUsername().equals(current.getPending(i)))
						{
							current.pending_array.add(ad_list.get(j));
							break;
							
						}
					}		
				}
				
			}	
		}
	}
	


public void BFS (User source,User dest) throws FileNotFoundException, IOException
{
	build();
	User s1 = null;
	int i;
	for (i=0;i<ad_list.size();i++)
	{
		if (ad_list.get(i).getUsername().equals(source.getUsername()))
		{
			s1 = ad_list.get(i);
			break;
		}
	}
	Queue<User> q = new LinkedList<User>();
	ad_list.get(i).change_value(0);
	ad_list.get(i).visit();
	q.add(ad_list.get(i));
	int flag=0;
	while (!q.isEmpty())
	{
		User el = q.element();
		//System.out.println("fd");

		//System.out.println(el.getUsername());
		q.remove();
		if (el.friends_array.size()!=0)
		for (i=0;i<el.friends_array.size();i++)
		{
			int val=el.get_value();
			
			if (!el.friends_array.get(i).check_visited()){
				el.friends_array.get(i).change_value(val+1);
				el.friends_array.get(i).visit();
				q.add(el.friends_array.get(i));
				
			}
			if (el.friends_array.get(i).getUsername().equals(dest.getUsername()))
			{
				print_path(el.friends_array.get(i),s1);
				flag=1;
				break;
			}
				
			
		}
		else{
			flag=0;
		}
		if (flag==1)
		{
			break;
		}
	}
	if (flag==0)
	{
		System.out.println("NO PATH");
	}
	
}

public void print_path (User dest,User source)
{
	String arr[] = new String [100];
	User min = dest;
	User temp = dest;
	int index=0;
	while (!dest.getUsername().equals(source.getUsername())){
		for (int i=0;i<dest.friends_array.size();i++)
		{
			if (dest.friends_array.get(i).get_value()<min.get_value())
			{
				min=dest.friends_array.get(i);
			}
		}
		dest = min;
		arr[index]=min.getUsername();
		index++;
	}
	System.out.print("SHORTEST ROUTE : ");
	for (int j=index-1;j>=0;j--)
	{
		System.out.print(arr[j] + "->");
	}
	System.out.print(temp.getUsername() + "\n");
}
 
}
