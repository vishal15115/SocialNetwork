/*
 * VISHAL RAJ DUTTA  2015115
 * SAURABH KAPUR     2015087
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Social_network {

	static ArrayList<User> all_users = new ArrayList<User>();
	private static Scanner read;
	static User login (String name , String pass) throws IOException
	{
		
		int flag=0;
		User temp=null;
		for(int i=0;i<all_users.size();i++)
		{
			if(all_users.get(i).getUsername().equals(name)&&all_users.get(i).getPassword().equals(pass))
			{
				System.out.println(all_users.get(i).getDisplayname() + " LOGGED IN NOW");
				temp=all_users.get(i);
				System.out.println(temp.getStatus());

				flag=1;
			}
		}
		if(flag==0)
		{
			return null;		   

		}
		else
			return temp;	
	}
	
	static User signup (String id , String name , String pass) throws IOException
	{
		User new_user = new User (id,pass,name,0,null,0,null,null);
		all_users.add(new_user);
		FileWriter fw = new FileWriter("/Users/saurabhkapur/Documents/workspace/Social_network/src/input.txt",true);
		BufferedWriter bw = new BufferedWriter(fw);
		String app = id + "," + pass + "," + name + "," + "0" + "," + "0" +","+"no status"+"\n"; 
		bw.write(app);
		bw.close();
		System.out.println("REGISTRATION SUCCESSFUL. " + "USER " + new_user.getUsername() + " CREATED");
		return new_user;					
	}
	
	static void build () throws FileNotFoundException, IOException
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
	    		all_users.add(user);
			}
			
			User current;
			for(int k=0;k<all_users.size();k++)
			{
				all_users.get(k).change_value(Integer.MAX_VALUE);
				current=all_users.get(k);
				for(int i=0;i<current.no_friends;i++)
				{
					for(int j=0;j<all_users.size();j++)
					{
						if(all_users.get(j).getUsername().equals(current.getFriend(i)))
						{
							current.friends_array.add(all_users.get(j));
							break;
						}
					}		
				}			
				for(int i=0;i<current.no_pending_req;i++)
				{
					for(int j=0;j<all_users.size();j++)
					{
						if(all_users.get(j).getUsername().equals(current.getPending(i)))
						{
							current.pending_array.add(all_users.get(j));
							break;
							
						}
					}		
				}
				
			}	
		}
	}
	
	public static void main(String[] args) throws IOException  {
		read = new Scanner (System.in);
		build();
		System.out.println("Reading database file...");
		System.out.println("Network is ready.");
		User cur_user = null;
		int n;
		String name;
		String disp_name;
		String pass;
		Graph short_route = new Graph();
		while (true){
			System.out.println("1.SIGNUP");
			System.out.println("2.LOGIN");
			n=read.nextInt();
			switch(n)
			{
				case 1 :
					System.out.printf("PLEASE ENTER USER ID : ");
					name = read.next();
					System.out.printf("PLEASE ENTER DISPLAY NAME : ");
					disp_name = read.next();
					System.out.printf("PLEASE ENTER USER PASSWORD : ");
					pass = read.next();
					cur_user = signup (name,disp_name,pass);
					if (true)
						continue;
					break;
				case 2:
					System.out.printf("PLEASE ENTER USER ID : ");
					name = read.next();
					System.out.printf("PLEASE ENTER USER PASSWORD : ");
					pass = read.next();
					try{
					cur_user = login (name,pass);
					cur_user.getStatus();
					}
					catch (NullPointerException e)
					{
						System.out.println("USER DOES NOT EXIST\n");
						continue;
					}
					System.out.println("");

					
					break;
			}
			while (n!=5){
				System.out.println("1. LIST FRIENDS");
				System.out.println("2. SEARCH");
				System.out.println("3. UPDATE STATUS");
				System.out.println("4. PENDING REQUEST");
				System.out.println("5. LOGOUT");
				n = read.nextInt();
				switch(n)
				{
					case 1:
						cur_user.displayfriendsinfo();
						break;
					case 2:
						int option=1;
						System.out.println("ENTER NAME");

						String user_name=read.next();
						while (option==1){
							
							 User tobesearched=cur_user.Search(all_users, user_name);
								 
							 
							
							 
							if(cur_user.friendship==0)
							{   try{
								System.out.println(tobesearched.getUsername()+" IS NOT A FRIEND.");
								}
								catch(NullPointerException e)
								{
									System.out.println("NOT FOUND");
									break;
								}	
								short_route.BFS(cur_user, tobesearched);
								cur_user.Find_mutual(tobesearched);
								if(cur_user.isRequestPending(tobesearched)==1)
								{
									System.out.println("Request Pending");
									System.out.println("1.  CANCEL REQUEST");
									System.out.println("2.  BACK");
									
									option=read.nextInt();
									if(option==1)
									{
										cur_user.cancelRequest(tobesearched);
									}
								}
								else if (cur_user.isRequestPending(tobesearched)==-1)
								{
									System.out.println("IS A FRIEND.");
								}
								else
								{
									System.out.println("1.  SEND REQUEST");
									System.out.println("2.  BACK");
									option=read.nextInt();
									if(option==1)
									{
										cur_user.sendRequest(tobesearched);
									}
								}
								
								
								
								
							}
							else if(cur_user.friendship==1)
							{
								
								System.out.println(tobesearched.getUsername()+" IS A FRIEND.");
								System.out.println("DISPLAY NAME IS:  "+ tobesearched.getDisplayname());
								System.out.println(tobesearched.getStatus()+"\n"+"FRIENDS ARE:");
								tobesearched.displayfriendsinfo();
								cur_user.Find_mutual(tobesearched);
								
								System.out.println("b.  BACK");
								char tem = read.next().charAt(0);
								if (tem=='b')
									break;
								
							}
							else
							{
								System.out.println("NOT FOUND");
			
							}
						
						}	
						break;
					case 3:
						System.out.println("ENTER STATUS : ");
						String status = read.nextLine();
						status = read.nextLine();
						cur_user.update_status(status);
						System.out.println("STATUS UPDATED!!");
						break; 
					case 4:
						while (true){
							for(int i=1;i<=cur_user.pending_array.size();i++)
							{
								System.out.println(i+". "+cur_user.pending_array.get(i-1).getUsername());
							}
							if(cur_user.pending_array.size()==0)
								System.out.println("NO NEW FRIEND REQUESTS.");
							System.out.println("b.  BACK");
							char option_selected=read.next().charAt(0);
							if (option_selected == 'b')
								break;
							int t = option_selected - '0';
							User to_befriend =cur_user.pending_array.get(t-1);
							
							System.out.println("1.  ACCEPT"+"\n2.  REJECT"+"\nb.  BACK");
							
							option_selected=read.next().charAt(0);
							
							if(option_selected=='1')
							{
								cur_user.pending_to_friends_user(to_befriend);
			                    cur_user.pending_to_friends_other(to_befriend);
							}
							else if(option_selected=='2')
							cur_user.remove_pending(to_befriend);
							else
							{
								break;
							}
						}	
					case 5:
						System.out.println("USER "+cur_user.getUsername() + " LOGGED OUT SUCCESSFULLY");
						break;
						
						
						
				}
			}
		}	
	}	
}
