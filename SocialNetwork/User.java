/*
 * VISHAL RAJ DUTTA  2015115
 * SAURABH KAPUR     2015087
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class User {
	String userid;
	String password;
	String disp_name;
	int no_friends;
	int no_pending_req;
	String friends[];
	String pending [];
	ArrayList<User> friends_array=new ArrayList<User>();
	ArrayList<User> pending_array=new ArrayList<User>();
	int value;
	String status;
	int friendship;
	boolean visited;
	
	User (String userid , String password ,String disp_name ,int no_friends ,String friends[] ,int no_pending,String pending[],String status)
	{
		this.userid = userid;
		this.password = password;
		this.disp_name = disp_name;
		this.no_friends = no_friends;
		this.friends = friends;
		this.pending = pending;
		this.no_pending_req = no_pending; 
		this.status = status;
		this.value=Integer.MAX_VALUE;
		this.visited=false;
	}
	
	public User() {
		// TODO Auto-generated constructor stub
	}

	public void change_value (int val)
	{
		this.value=val;
	}
	public int get_value ()
	{
		return value;
	}
	
	public void visit()
	{
		visited=true;
	}
	public void unvisited ()
	{
		visited = false;
	}
	
	public boolean check_visited ()
	{
		return visited;
	}
	
	public User Search(ArrayList<User> all_user,String user_name)
	{
		int flag1=0;
		int flag2=0;
		User temp1=null,temp2=null;//new additions,friendship added to attribute
		for(int i=0;i<all_user.size();i++)
		{
			if (i<no_friends && friends_array.get(i).getUsername().equals(user_name))
			{
				flag1=1;
				temp1=friends_array.get(i);
			}
			if(all_user.get(i).getUsername().equals(user_name))
			{
				flag2=1;
				temp2=all_user.get(i);

			}
			
		}
		if (flag1==1 && flag2==1)
		{
			friendship= 1;

			return temp1;
		}
		else if (flag2==1)
		{
			 friendship=0;
			 return temp2;
		}
		else{
			friendship=0;
			return null;
		}
	}
	
	

	
	public void Find_mutual(User tobesearched)
	{
		ArrayList<User> mutualfriends=new ArrayList<User>();
		
		for(int i=0;i<no_friends;i++)
		{
			for(int j=0;j<tobesearched.no_friends;j++)
			{
				if(this.friends_array.get(i).equals(tobesearched.friends_array.get(j)))
				{
					mutualfriends.add(this.friends_array.get(i));
					break;
				}
			}
		}
		
		
		if(mutualfriends.size()!=0)
		{  
			System.out.print("MUTUAL FRIENDS: ");

			for(int i=0;i<mutualfriends.size();i++)
			{
				System.out.println(mutualfriends.get(i).getUsername());
			}
		}
		else
		{
			System.out.println("NO MUTUAL FRIENDS");
		}
	}
	
	
	
	public void list_friends ()
	{
		 for (String s: friends)
	     {
			 if (s == null)
				 break;
			 System.out.println(s);
	     }
	}
	
	public void disp_info ()
	{   
		System.out.println(this.userid );
	}
	
	public String getUsername(){
		return userid;
	}
	
	public String getDisplayname(){
		return disp_name;
	}
	
	public String getPassword()
	
		{
			return password;
		
	}
	
	public String getStatus()
	
	{
		return status;
	
}
	
	public String getFriend(int index)
	{
		return friends[index];
	}
	
	public String getPending(int index)
	{
		return pending[index];
	}
	
	public void displayfriendsinfo()
	{ System.out.println("YOUR FRIENDS ARE:");
		for(int i=0;i<no_friends;i++)
		{
			friends_array.get(i).disp_info();
		}
		System.out.println("\n");
	}
	
	public void update_status (String status) throws IOException
	{
		this.status = status;
		
		FileWriter fw = new FileWriter("/Users/saurabhkapur/Documents/workspace/Social_network/src/temp.txt",true);
		BufferedWriter bw = new BufferedWriter(fw);
		try(BufferedReader br = new BufferedReader(new FileReader("/Users/saurabhkapur/Documents/workspace/Social_network/src/input.txt"))){
		for(String line; (line = br.readLine()) != null; ) {
	    	String []a = line.split(",");
	    	if (a[0].equals(userid))
	    	{
	    		String temp = userid + "," + password + "," + disp_name + "," + no_friends + "," ;
	    		for (int i=0; i<no_friends;i++)
	    		{
	    			temp = temp + friends[i] + ",";
	    		}
	    		temp = temp + no_pending_req + ","; 
	    		for (int i=0; i<no_pending_req;i++)
	    		{
	    			temp = temp + pending[i] + ",";
	    		}
	    		temp = temp + status + "\n";
	    		bw.write(temp);
	    	}
	    	else
	    	{
	    		bw.write(line);
	    		bw.write("\n");
	    	}
		}
		File oldName = new File("/Users/saurabhkapur/Documents/workspace/Social_network/src/temp.txt");
	    File newName = new File("/Users/saurabhkapur/Documents/workspace/Social_network/src/input.txt");
	    if(oldName.renameTo(newName)) {
	        // System.out.println("renamed");
	    }
		bw.close();
	    br.close();
	      
		}
		catch(IOException e){
			System.out.println("ss ");
		}
		
	}
	
	public int isRequestPending(User in_focus)
	{
		for(int i=0;i<in_focus.pending_array.size();i++)
		{
			if(in_focus.pending_array.get(i).equals(this))
				return 1;//yes.pending
		}
		for(int j=0;j<in_focus.friends_array.size();j++)
		{
			if(in_focus.friends_array.get(j).equals(this))
				return -1;//already friends.request accepted
		}
		return 0;//didnt send any request.so should prmpt for request.
	}
	
	
	public void pending_to_friends_user(User to_befriend) throws IOException
	{
		//this.status = status;
		int flag=0;
		for(int i=0;i<no_pending_req;i++)
		{
			if(pending_array.get(i).equals(to_befriend))
			{
				friends_array.add(pending_array.get(i));
				pending_array.remove(i);
				no_friends+=1;
				no_pending_req-=1;
				flag=1;
				break;
			}
			
		}
		
		if(flag==0)
		{
			System.out.println("not found");
			
		}
		else
		{
			
		//System.out.println("heere making vhage to pfriends");
		FileWriter fw = new FileWriter("/Users/saurabhkapur/Documents/workspace/Social_network/src/temp.txt",true);
		BufferedWriter bw = new BufferedWriter(fw);
		try(BufferedReader br = new BufferedReader(new FileReader("/Users/saurabhkapur/Documents/workspace/Social_network/src/input.txt"))){
		for(String line; (line = br.readLine()) != null; ) {
	    	String []a = line.split(",");
	    	if (a[0].equals(userid))
	    	{
	    		String temp = userid + "," + password + "," + disp_name + "," + no_friends + "," ;
	    		for (int i=0; i<no_friends;i++)
	    		{
	    			temp = temp + friends_array.get(i).getUsername() + ",";
	    		}
	    		temp = temp + no_pending_req+","; 
	    		for (int i=0; i<no_pending_req;i++)
	    		{
	    			temp = temp + pending_array.get(i).getUsername() + ",";
	    		}
	    		temp = temp + status + "\n";
	    		bw.write(temp);
	    	}
	    	else
	    	{
	    		bw.write(line);
	    		bw.write("\n");
	    	}
		}

	    bw.close();
	    br.close();
	    File oldName = new File("/Users/saurabhkapur/Documents/workspace/Social_network/src/temp.txt");
	    File newName = new File("/Users/saurabhkapur/Documents/workspace/Social_network/src/input.txt");
	    if(oldName.renameTo(newName)) {
	        // System.out.println("renamed");
	    }
	      
		}
		catch(IOException e){
			System.out.println("ss ");
		}
		 
	}
		
	}
	
	
	
	public void pending_to_friends_other(User to_befriend) throws IOException
	{
		//this.status = status;
		to_befriend.friends_array.add(this);
		to_befriend.no_friends+=1;
		
			
		//System.out.println("heere making vhage to pfriends");
		FileWriter fw = new FileWriter("/Users/saurabhkapur/Documents/workspace/Social_network/src/temp.txt",true);
		BufferedWriter bw = new BufferedWriter(fw);
		try(BufferedReader br = new BufferedReader(new FileReader("/Users/saurabhkapur/Documents/workspace/Social_network/src/input.txt"))){
		for(String line; (line = br.readLine()) != null; ) {
	    	String []a = line.split(",");
	    	if (a[0].equals(to_befriend.getUsername()))
	    	{
	    		String temp = to_befriend.userid + "," + to_befriend.password + "," + to_befriend.disp_name + "," + to_befriend.no_friends + "," ;
	    		for (int i=0; i<to_befriend.no_friends;i++)
	    		{
	    			temp = temp + to_befriend.friends_array.get(i).getUsername() + ",";
	    		}
	    		temp = temp + to_befriend.no_pending_req +","; 
	    		for (int i=0; i<to_befriend.no_pending_req;i++)
	    		{
	    			temp = temp + to_befriend.pending_array.get(i).getUsername() + ",";
	    		}
	    		temp = temp + to_befriend.status + "\n";
	    		bw.write(temp);
	    	}
	    	else
	    	{
	    		bw.write(line);
	    		bw.write("\n");
	    	}
		}
		File oldName = new File("/Users/saurabhkapur/Documents/workspace/Social_network/src/temp.txt");
	    File newName = new File("/Users/saurabhkapur/Documents/workspace/Social_network/src/input.txt");
	    if(oldName.renameTo(newName)) {
	    }
	    bw.close();
	    br.close();
	      
		}
		catch(IOException e){
			System.out.println("ss ");
		}
		 
	
		
	}
	
	public void sendRequest(User tobesearched) throws IOException
	{
		int flag=0;
		
		for(int i=0;i<tobesearched.pending_array.size();i++)
		{
			if(this.equals(tobesearched.pending_array.get(i)))
			{
				flag=1;
			}
		}
			
		if(flag==0)
		{
			
			tobesearched.pending_array.add(this);
			tobesearched.no_pending_req+=1;			
			FileWriter fw = new FileWriter("/Users/saurabhkapur/Documents/workspace/Social_network/src/temp.txt",true);
			BufferedWriter bw = new BufferedWriter(fw);
			try(BufferedReader br = new BufferedReader(new FileReader("/Users/saurabhkapur/Documents/workspace/Social_network/src/input.txt"))){
			for(String line; (line = br.readLine()) != null; ) {
		    	String []a = line.split(",");
		    	if (a[0].equals(tobesearched.getUsername()))
		    	{
		    		String temp = new String();
		    		System.out.println(tobesearched.userid);
		    		temp = tobesearched.userid + "," + tobesearched.password + "," + tobesearched.disp_name + "," + tobesearched.no_friends + "," ;
		    		for (int i=0; i<tobesearched.no_friends;i++)
		    		{
		    			temp = temp + tobesearched.friends_array.get(i).getUsername() + ",";
		    		}
		    		temp = temp + tobesearched.no_pending_req; 
		    		for (int i=0; i<tobesearched.no_pending_req;i++)
		    		{
		    			temp = temp + "," + tobesearched.pending_array.get(i).getUsername() ;
		    		}
		    		temp = temp + ","+ tobesearched.status + "\n";
		    		bw.write(temp);
		    	}
		    	else
		    	{
		    		bw.write(line);
		    		bw.write('\n');
		    	}
			}
			File oldName = new File("/Users/saurabhkapur/Documents/workspace/Social_network/src/temp.txt");
		    File newName = new File("/Users/saurabhkapur/Documents/workspace/Social_network/src/input.txt");
		    if(oldName.renameTo(newName)) {
		        // System.out.println("renamed");
		    }
		    bw.close();
		    br.close();
		      System.out.println("REQUEST SENT");
			}
			catch(IOException e){
				//System.out.println("ss ");
			}
			
		}
			
			
				
				
				
				
		
	
		
	
	}
	
	
	public void cancelRequest(User tobesearched) throws IOException
	{
		tobesearched.pending_array.remove(this);
		tobesearched.no_pending_req-=1;
		
	
		
		FileWriter fw = new FileWriter("/Users/saurabhkapur/Documents/workspace/Social_network/src/temp.txt",true);
		BufferedWriter bw = new BufferedWriter(fw);
		try(BufferedReader br = new BufferedReader(new FileReader("/Users/saurabhkapur/Documents/workspace/Social_network/src/input.txt"))){
		for(String line; (line = br.readLine()) != null; ) {
	    	String []a = line.split(",");
	    	if (a[0].equals(tobesearched.getUsername()))
	    	{
	    		String temp = tobesearched.userid + "," + tobesearched.password + "," + tobesearched.disp_name + "," + tobesearched.no_friends + "," ;
	    		for (int i=0; i<tobesearched.no_friends;i++)
	    		{
	    			temp = temp + tobesearched.friends_array.get(i).getUsername() + ",";
	    		}
	    		temp = temp + tobesearched.no_pending_req; 
	    		
	    		for (int i=0; i<tobesearched.no_pending_req;i++)
	    		{
	    			temp = temp + "," + tobesearched.pending_array.get(i).getUsername() ;
	    		}
	    		temp = temp + ","+ tobesearched.status + "\n";
	    		bw.write(temp);
	    	}
	    	else
	    	{
	    		bw.write(line);
	    		bw.write('\n');
	    	}
		}
		File oldName = new File("/Users/saurabhkapur/Documents/workspace/Social_network/src/temp.txt");
	    File newName = new File("/Users/saurabhkapur/Documents/workspace/Social_network/src/input.txt");
	    if(oldName.renameTo(newName)) {
	        // System.out.println("renamed");
	    }
	    bw.close();
	    br.close();
	      System.out.println("FRIEND REQUEST TO "+tobesearched.getUsername()+ " CANCELED." );
		}
		catch(IOException e){
			//System.out.println("ss ");
		}
		
	}
	public void remove_pending(User to_reject) throws IOException
	{
		//this.status = status;
		int flag=0;
		for(int i=0;i<no_pending_req;i++)
		{
			if(this.pending_array.get(i).equals(to_reject))
			{
				
				pending_array.remove(i);
				no_pending_req-=1;
				flag=1;
			      System.out.println("FRIEND REQUEST BY "+to_reject.getUsername()+ " DELETED." );
				break;
			}
			
		}
		
		if(flag==0)
		{
			System.out.println("NOT FOUND");
			
		}
		else
		{
			
		
		FileWriter fw = new FileWriter("/Users/saurabhkapur/Documents/workspace/Social_network/src/temp.txt",true);
		BufferedWriter bw = new BufferedWriter(fw);
		try(BufferedReader br = new BufferedReader(new FileReader("/Users/saurabhkapur/Documents/workspace/Social_network/src/input.txt"))){
		for(String line; (line = br.readLine()) != null; ) {
	    	String []a = line.split(",");
	    	if (a[0].equals(userid))
	    	{
	    		String temp = userid + "," + password + "," + disp_name + "," + no_friends + "," ;
	    		for (int i=0; i<no_friends;i++)
	    		{
	    			temp = temp + friends_array.get(i).getUsername() + ",";
	    		}
	    		temp = temp + no_pending_req; 
	    		
	    		for (int i=0; i<no_pending_req;i++)
	    		{
	    			temp = temp + "," + pending_array.get(i).getUsername() ;
	    		}
	    		temp = temp + ","+ status + "\n";
	    		bw.write(temp);
	    	}
	    	else
	    	{
	    		bw.write(line);
	    		bw.write('\n');
	    	}
		}
		File oldName = new File("/Users/saurabhkapur/Documents/workspace/Social_network/src/temp.txt");
	    File newName = new File("/Users/saurabhkapur/Documents/workspace/Social_network/src/input.txt");
	    if(oldName.renameTo(newName)) {
	        // System.out.println("renamed");
	    }
	    bw.close();
	    br.close();
	      
		}
		catch(IOException e){
			System.out.println("ss ");
		}
		 
	}
		
	}
}
