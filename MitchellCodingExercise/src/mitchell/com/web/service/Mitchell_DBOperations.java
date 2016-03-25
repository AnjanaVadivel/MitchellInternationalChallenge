package mitchell.com.web.service;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;
 
public class Mitchell_DBOperations {
	
	private static Connection dbConnection;
	
	Mitchell_DBOperations()
	{
		
	}
    
    Mitchell_DBOperations(Connection dbConn){
        this.dbConnection = dbConn;
    }
     
    public void fnOperationList(){
         
        System.out.println("\n----------Operations List----------\nEnter [1 to 8] to select the operation\n");
         
        System.out.println("[1] Create and Store Claim");
        System.out.println("[2] Read Claim");
        System.out.println("[3] Find List of Claims");
        System.out.println("[4] Update Claim");
        System.out.println("[5] Read a specific vehicle from specific claim");
        System.out.println("[6] Delete Claim");
                         
        Scanner input = new Scanner(System.in);
         
        System.out.print("Enter your option: ");
        int dbOp = 0;
        try{
        	dbOp = input.nextInt();
        }
        catch (InputMismatchException e){
            dbOp = 0;
        }
        switch(dbOp){
         
        case 1: 
            fnCreateStoreClaims(); 
            break;
        case 2:
            fnReadClaim();
            break;
        case 3:
            fnFindClaims();
            break;
        case 4:
            fnUpdateClaim();
            break;
        case 5:
            fnReadSpecificClaim();
            break;
        case 6:
            fnDeleteClaim();
            break;        
        default: 
            System.out.println("ERROR: Invalid Input!");
            fnOperationList(); 
            break;         
        }
        
        System.out.println("----------------------------------------------------------------------------- End of Operation ------------------------------------------------------------------------\n");
    }
    
    /*Global variables used to store the xml parsed contents*/
    String Claim_number = "";	     
    String Claimant_First_Name = "";	        	         
    String Claimant_Last_Name = "";	        	         
    String Status = "";    
    String Loss_Date = "";
    String Assigned_Adjuster_ID = "";
    
    String Cause_Of_Loss = "";
    String Reported_Date = "";
    String Loss_Description = "";
    
    String Vin = "";        	         
    String Model_Year = "";	        	         
    String Make_Description = ""; 
    String Model_Description = "";
    String Engine_Description = "";
    String Exterior_Color = "";
    String Lic_Plate = "";
    String Lic_Plate_State = "";
    String Lic_Plate_Exp_Date = "";
    String Damage_Description = "";
    String Mileage = "";
        
	public void fnCreateStoreClaims(){
		 System.out.println("\n------------------ Create and Store Claim:---------------");
	      try {	
	    	 ResultSet rs;
	         File inputFile = new File("D://Mitchell//create-claim.xml");
	         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	         Document doc = dBuilder.parse(inputFile);
	         doc.getDocumentElement().normalize();

	         Claim_number = doc.getElementsByTagName("cla:ClaimNumber").item(0).getTextContent();	     
	         Claimant_First_Name = doc.getElementsByTagName("cla:ClaimantFirstName").item(0).getTextContent();	        	         
	         Claimant_Last_Name = doc.getElementsByTagName("cla:ClaimantLastName").item(0).getTextContent();	        	         
	         Status = doc.getElementsByTagName("cla:Status").item(0).getTextContent();	         
	         Loss_Date = doc.getElementsByTagName("cla:LossDate").item(0).getTextContent();
	         //System.out.println(Loss_Date);
	         //2014-07-09T17:19:13.631-07:00
	         SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.US);
	         java.util.Date utilDate = df.parse(Loss_Date);
	         java.sql.Date result = new java.sql.Date(utilDate.getTime());         
	         
	         Node nNode = doc.getElementsByTagName("cla:LossInfo").item(0);
	         if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	               Element eElement = (Element) nNode;
	               Cause_Of_Loss = eElement.getElementsByTagName("cla:CauseOfLoss").item(0).getTextContent();	        	         
	  	           Reported_Date = eElement.getElementsByTagName("cla:ReportedDate").item(0).getTextContent();	        	         
	  	           Loss_Description = eElement.getElementsByTagName("cla:LossDescription").item(0).getTextContent();
	         }
	         
	         String Assigned_Adjuster_ID = doc.getElementsByTagName("cla:AssignedAdjusterID").item(0).getTextContent();
	         
	         Node nNode1 = doc.getElementsByTagName("cla:Vehicles").item(0);
	         if (nNode1.getNodeType() == Node.ELEMENT_NODE) {	        	 
	               Element eElement = (Element) nNode1;	         
	               Vin = eElement.getElementsByTagName("cla:Vin").item(0).getTextContent();	        	         
	  	           Model_Year = eElement.getElementsByTagName("cla:ModelYear").item(0).getTextContent();	        	         
	  	           Make_Description = eElement.getElementsByTagName("cla:MakeDescription").item(0).getTextContent();
	  	           Model_Description = eElement.getElementsByTagName("cla:ModelDescription").item(0).getTextContent();
	  	           Engine_Description = eElement.getElementsByTagName("cla:EngineDescription").item(0).getTextContent();
	  	           Exterior_Color = eElement.getElementsByTagName("cla:ExteriorColor").item(0).getTextContent();
	  	           Lic_Plate = eElement.getElementsByTagName("cla:LicPlate").item(0).getTextContent();
	  	           Lic_Plate_State = eElement.getElementsByTagName("cla:LicPlateState").item(0).getTextContent();
	  	           Lic_Plate_Exp_Date = eElement.getElementsByTagName("cla:LicPlateExpDate").item(0).getTextContent();
	  	           Damage_Description = eElement.getElementsByTagName("cla:DamageDescription").item(0).getTextContent();
	  	           Mileage = eElement.getElementsByTagName("cla:Mileage").item(0).getTextContent();	  	 
	         }         
	         
	            PreparedStatement pstmt = dbConnection.prepareStatement("INSERT INTO claimtype VALUES (?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
	            pstmt.clearParameters();
	            pstmt.setString(1, Claim_number);
	            if(Claimant_First_Name.equals("na"))
	                pstmt.setString(2, null);
	            else
	                pstmt.setString(2, Claimant_First_Name);
	            if(Claimant_Last_Name.equals("na"))
	                pstmt.setString(3, null);
	            else
	                pstmt.setString(3, Claimant_Last_Name);
	            if(Status.equals("na"))
	                pstmt.setString(4, null);
	            else
	                pstmt.setString(4, Status);	            
	            if(result.equals("na"))
	                pstmt.setDate(5, null);
	            else
	                pstmt.setDate(5, result);
	            if(Assigned_Adjuster_ID.equals("na"))
	                pstmt.setString(6, null);
	            else
	                pstmt.setString(6, Assigned_Adjuster_ID);
	           
	            pstmt.executeUpdate();
	                           
	                PreparedStatement pstmt1 = dbConnection.prepareStatement("INSERT INTO loss_info_type VALUES (?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
		            pstmt1.clearParameters();
		            if(Cause_Of_Loss.equals("na"))
		                pstmt1.setString(1, null);
		            else
		                pstmt1.setString(1, Cause_Of_Loss);
		            if(Reported_Date.equals("na"))
		                pstmt1.setString(2, null);
		            else
		                pstmt1.setString(2, Reported_Date);
		            if(Loss_Description.equals("na"))
		                pstmt1.setString(3, null);
		            else
		                pstmt1.setString(3, Loss_Description);	
		            if(Claim_number.equals("na"))
		                pstmt1.setString(4, null);
		            else
		                pstmt1.setString(4, Claim_number);	
		            pstmt1.executeUpdate();
		            
		            PreparedStatement pstmt2 = dbConnection.prepareStatement("INSERT INTO vehicle_info_type VALUES (?,?,?,?,?,?,?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
		            pstmt2.clearParameters();
		            if(Vin.equals("na"))
		                pstmt2.setString(1, null);
		            else
		                pstmt2.setString(1, Vin);
		            if(Model_Year.equals("na"))
		                pstmt2.setString(2, null);
		            else
		                pstmt2.setString(2, Model_Year);
		            if(Make_Description.equals("na"))
		                pstmt2.setString(3, null);
		            else
		                pstmt2.setString(3, Make_Description);
		            if(Model_Description.equals("na"))
		                pstmt2.setString(4, null);
		            else
		                pstmt2.setString(4, Model_Description);
		            if(Engine_Description.equals("na"))
		                pstmt2.setString(5, null);
		            else
		                pstmt2.setString(5, Engine_Description);
		            if(Exterior_Color.equals("na"))
		                pstmt2.setString(6, null);
		            else
		                pstmt2.setString(6, Exterior_Color);
		            if(Lic_Plate.equals("na"))
		                pstmt2.setString(7, null);
		            else
		                pstmt2.setString(7, Lic_Plate);
		            if(Lic_Plate_State.equals("na"))
		                pstmt2.setString(8, null);
		            else
		                pstmt2.setString(8, Lic_Plate_State);
		            if(Lic_Plate_Exp_Date.equals("na"))
		                pstmt2.setString(9, null);
		            else
		                pstmt2.setString(9, Lic_Plate_Exp_Date);
		            if(Damage_Description.equals("na"))
		                pstmt2.setString(10, null);
		            else
		                pstmt2.setString(10, Damage_Description);
		            if(Mileage.equals("na"))
		                pstmt2.setString(11, null);
		            else
		                pstmt2.setString(11, Mileage);
		            if(Claim_number.equals("na"))
		                pstmt2.setString(12, null);
		            else
		                pstmt2.setString(12, Claim_number);	            
		            pstmt2.executeUpdate();	        
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	}
	
	public void fnReadClaim(){
		 System.out.println("\n------------------Read Claim:---------------");
	     Scanner input = new Scanner(System.in);
		 System.out.print("Enter the Claim Number");
		 String Claim_Number=input.nextLine();	 
		 ResultSet rs;
   		try
		{
			PreparedStatement pstmt = dbConnection.prepareStatement("Select * from claimtype join loss_info_type on claimtype.claim_number=loss_info_type.claim_number "
					+ "join vehicle_info_type on loss_info_type.claim_number=vehicle_info_type.claim_number where claimtype.claim_number=?",Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, Claim_Number);
			rs=pstmt.executeQuery();
			
			boolean nextFlag = rs.next();
        	
        	if(!nextFlag)
        		System.out.println("\nMSG: No results to display.");
        		
            while(nextFlag){                 
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++){
                    System.out.println(rs.getMetaData().getColumnLabel(i)+": "+rs.getString(i));
                    System.out.println();
                }                 
                nextFlag = rs.next();
            }
		}catch(SQLException e)
        {
            System.out.println("ERROR: " + e.getMessage());
          fnOperationList();
        }		
	}
	
	public void fnFindClaims(){
		 System.out.println("\n------------------Find Claims:---------------");
	     Scanner input = new Scanner(System.in);
		 System.out.print("Enter the start date");
		 String Start_Date=input.nextLine();	
		 System.out.print("Enter the end date");
		 String End_Date=input.next();
		 ResultSet rs;
  		try
		{
			PreparedStatement pstmt = dbConnection.prepareStatement("Select claim_number from claimtype "
					+ "where loss_date between" + " " + "'" + Start_Date + "'" + " " +"and" + " " + "'" + 
					End_Date + "'" + " " + "order by loss_date desc");
			rs=pstmt.executeQuery();
			
			boolean nextFlag = rs.next();
       	
       	    if(!nextFlag)
       		System.out.println("\nMSG: No results to display.");
       		
       	    while(nextFlag){                 
               for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++){
                   System.out.println(rs.getMetaData().getColumnLabel(i)+": "+rs.getString(i));
                   System.out.println();
               }                 
               nextFlag = rs.next();
           }
		}catch(SQLException e)
       {
           System.out.println("ERROR: " + e.getMessage());
         fnOperationList();
       }		
	}
	
	
	public void fnUpdateClaim()
	{
	 System.out.println("\n------------------ Update Claim:---------------");
     try {	    	 
    	ResultSet rs;
        File inputFile = new File("D://Mitchell//update-claim.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(inputFile);
        doc.getDocumentElement().normalize();
        java.sql.Date result=null;

        Node objects = doc.getDocumentElement();
        for (Node object = objects.getFirstChild(); object != null; object = object.getNextSibling()) {
            if (object instanceof Element) {
                Element e = (Element)object;
                if (e.getTagName().equalsIgnoreCase("cla:ClaimNumber")) {
                    Claim_number = e.getTextContent();
                    
                    PreparedStatement pstmt = dbConnection.prepareStatement("Select * from claimtype "
                	 		+ "where claim_number=?",Statement.RETURN_GENERATED_KEYS);
            			pstmt.setString(1, Claim_number);
            			rs=pstmt.executeQuery();
            			
            			boolean nextFlag = rs.next();
                 	   	if(!nextFlag)
                 	   	{
                 	   		
                 	   	}
                 	   	else
                 	   	{                        
                         Claimant_First_Name = rs.getString(2);
                         Claimant_Last_Name=rs.getString(3);
                         Status=rs.getString(4);
                         result=rs.getDate(5);
                         Assigned_Adjuster_ID=rs.getString(6);
                 	   	}
                 	
                 	 PreparedStatement pstmt1 = dbConnection.prepareStatement("Select * from loss_info_type "
                   	 		+ "where claim_number=?",Statement.RETURN_GENERATED_KEYS);
               			pstmt1.setString(1, Claim_number);
               			rs=pstmt1.executeQuery();
               			
               			boolean nextFlag1 = rs.next();
                    	   	if(!nextFlag1)
                    	   	{
                    	   		
                    	   	}
                    	   	else
                    	   	{                        
                            Cause_Of_Loss = rs.getString(1);
                            Reported_Date=rs.getString(2);
                            Loss_Description=rs.getString(3);                            
                    	   	}
                    	   	
                     PreparedStatement pstmt2 = dbConnection.prepareStatement("Select * from vehicle_info_type "
                        	 		+ "where claim_number=?",Statement.RETURN_GENERATED_KEYS);
                    			pstmt2.setString(1, Claim_number);
                    			rs=pstmt2.executeQuery();
                    			
                    			boolean nextFlag2 = rs.next();
                         	   	if(!nextFlag2)
                         	   	{
                         	   		
                         	   	}
                         	   	else
                         	   	{                        
                                 Model_Year = rs.getString(1);	        	         
                                 Make_Description = rs.getString(2); 
                                 Model_Description = rs.getString(3);
                                 Engine_Description = rs.getString(4);
                                 Exterior_Color = rs.getString(5);
                                 Vin = rs.getString(6);       
                                 Lic_Plate = rs.getString(7);
                                 Lic_Plate_State = rs.getString(8);
                                 Lic_Plate_Exp_Date = rs.getString(9);
                                 Damage_Description = rs.getString(10);
                                 Mileage = rs.getString(11);
                         	   	}
                } else if (e.getTagName().equalsIgnoreCase("cla:ClaimantFirstName")) {
                	Claimant_First_Name = e.getTextContent();                    
                } else if(e.getTagName().equalsIgnoreCase("cla:ClaimantLastName")){
                	Claimant_Last_Name = e.getTextContent();
                }else if(e.getTagName().equalsIgnoreCase("cla:Status")){
                	Status = e.getTextContent();
                }else if(e.getTagName().equalsIgnoreCase("cla:LossDate")){
                	Loss_Date=e.getTextContent(); 
                	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.US);
                    java.util.Date utilDate = df.parse(Loss_Date);
                    result = new java.sql.Date(utilDate.getTime());
                }else if(e.getTagName().equalsIgnoreCase("cla:AssignedAdjusterID")){
                	Assigned_Adjuster_ID = e.getTextContent();
                }else if(e.getTagName().equalsIgnoreCase("cla:LossInfo")){
                    Node objects1 = e;
                	for (Node object1 = objects1.getFirstChild(); object1 != null; object1 = object1.getNextSibling()) {
                        if (object1 instanceof Element) {
                            Element e1 = (Element)object1;
                            if(e1.getTagName().equalsIgnoreCase("cla:CauseOfLoss")){
                            Cause_Of_Loss = e1.getTextContent();
                            }else if(e1.getTagName().equalsIgnoreCase("cla:ReportedDate")){
                             Reported_Date = e1.getTextContent();
                            }else if(e1.getTagName().equalsIgnoreCase("cla:LossDescription")){
                             Loss_Description = e1.getTextContent();
                            }
                        }
                	}
                }else if(e.getTagName().equalsIgnoreCase("cla:Vehicles")){
                	Node objects2 = e;
                	for(Node object2 = objects2.getFirstChild(); object2 !=null; object2 = object2.getNextSibling()){
                		if(object2 instanceof Element){
                			Element e2 = (Element)object2;
                			if(e2.getTagName().equalsIgnoreCase("cla:Vin")){
                                	Vin = e2.getTextContent();
                                }else if(e2.getTagName().equalsIgnoreCase("cla:ModelYear")){
                                	Model_Year = e2.getTextContent();
                                }else if(e2.getTagName().equalsIgnoreCase("cla:MakeDescription")){
                                	Make_Description = e2.getTextContent();
                                }else if(e2.getTagName().equalsIgnoreCase("cla:ModelDescription")){
                                	Model_Description = e2.getTextContent();
                                }else if(e2.getTagName().equalsIgnoreCase("cla:EngineDescription")){
                                	Engine_Description = e2.getTextContent();
                                }else if(e2.getTagName().equalsIgnoreCase("cla:ExteriorColor")){
                                	Exterior_Color = e2.getTextContent();
                                }else if(e2.getTagName().equalsIgnoreCase("cla:LossDescription")){
                                	Loss_Description = e2.getTextContent();
                                }else if(e2.getTagName().equalsIgnoreCase("cla:LicPlate")){
                                	Lic_Plate = e2.getTextContent();
                                }else if(e2.getTagName().equalsIgnoreCase("cla:LicPlateState")){
                                	Lic_Plate_State = e2.getTextContent();
                                }else if(e2.getTagName().equalsIgnoreCase("cla:LicPlateExpDate")){
                                	Lic_Plate_Exp_Date = e2.getTextContent();
                                }else if(e2.getTagName().equalsIgnoreCase("cla:DamageDescription")){
                                	Damage_Description = e2.getTextContent();
                                }else if(e2.getTagName().equalsIgnoreCase("cla:Mileage")){
                                	Mileage = e2.getTextContent();
                                }
                			}                			
                		}
                	}
                }                
            }
        
           PreparedStatement pstmt = dbConnection.prepareStatement("UPDATE claimdb.claimtype set claimant_first_name=? , claimant_last_name=? , status=? ,"
           		+ "loss_date=? , assigned_adjuster_id=? where claim_number=?",Statement.RETURN_GENERATED_KEYS);
           pstmt.clearParameters();
           pstmt.setString(1, Claimant_First_Name);
           pstmt.setString(2, Claimant_Last_Name);
           pstmt.setString(3, Status);
           pstmt.setDate(4, result);
           pstmt.setString(5, Assigned_Adjuster_ID);
           pstmt.setString(6, Claim_number);
           pstmt.executeUpdate();
           
           PreparedStatement pstmt1 = dbConnection.prepareStatement("UPDATE claimdb.loss_info_type set cause_of_loss=? , "
           		+ "reported_date=? , loss_description=? where claim_number=?",Statement.RETURN_GENERATED_KEYS);
              pstmt1.clearParameters();
              pstmt1.setString(1, Cause_Of_Loss);
              pstmt1.setString(2, Reported_Date);
              pstmt1.setString(3, Loss_Description);
              pstmt1.setString(4, Claim_number);
              pstmt1.executeUpdate();
              
              PreparedStatement pstmt2 = dbConnection.prepareStatement("UPDATE claimdb.vehicle_info_type set model_year=? , "
                 		+ "make_description=? , model_description=? , engine_description=? , exterior_color=? ,"
                 		+ "vin=? , lic_plate=? , lic_plate_slate=? , lic_plate_exp_date=? , damage_description=? , "
                 		+ "mileage=? where claim_number=?",Statement.RETURN_GENERATED_KEYS);
                    pstmt2.clearParameters();
                    pstmt2.setString(1,Model_Year);
                    pstmt2.setString(2, Make_Description);
                    pstmt2.setString(3, Model_Description);
                    pstmt2.setString(4, Engine_Description);
                    pstmt2.setString(5, Exterior_Color);
                    pstmt2.setString(6, Vin);
                    pstmt2.setString(7, Lic_Plate);
                    pstmt2.setString(8, Lic_Plate_State);
                    pstmt2.setString(9, Lic_Plate_Exp_Date);
                    pstmt2.setString(10, Damage_Description);
                    pstmt2.setString(11, Mileage);
                    pstmt2.setString(12, Claim_number);                            
                    pstmt2.executeUpdate();	      
     				} catch (Exception e) {
     				e.printStackTrace();
     				}
				}
	
	
	
	public void fnReadSpecificClaim(){
		 System.out.println("\n------------------Read Specific Claim:---------------");
	     Scanner input = new Scanner(System.in);
		 System.out.println("Enter the Claim Number");
		 String Claim_Number=input.nextLine();	 
		 System.out.println("Enter the Vehicle Model Year");
		 String Model_Year=input.next();
		 ResultSet rs;
  		try
		{
			PreparedStatement pstmt = dbConnection.prepareStatement("Select * from vehicle_info_type where claim_number=? AND model_year=?",Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, Claim_Number);
			pstmt.setString(2,Model_Year);
			rs=pstmt.executeQuery();
			
			boolean nextFlag = rs.next();
       	
			if(!nextFlag)
       		System.out.println("\nMSG: No results to display.");
       		
			while(nextFlag){                 
               for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++){
                   System.out.println(rs.getMetaData().getColumnLabel(i)+": "+rs.getString(i));
                   System.out.println();
               }                 
               nextFlag = rs.next();
           }
		}catch(SQLException e)
       {
           System.out.println("ERROR: " + e.getMessage());
           fnOperationList();
       }				
	}
	
	
   public void fnDeleteClaim(){
	   System.out.println("\n------------------Delete Claim:---------------");
       Scanner input = new Scanner(System.in);
       System.out.print("Enter the Claim Number: ");
       String Claim_Number=input.nextLine();       
       try{
           PreparedStatement pstmt=dbConnection.prepareStatement("DELETE FROM claimtype WHERE claim_number=?",Statement.RETURN_GENERATED_KEYS);
           pstmt.setString(1,Claim_Number);           
           pstmt.executeUpdate();
           }catch(SQLException e)
           {
               System.out.println("ERROR: " + e.getMessage());
               fnOperationList();
           }       
   }
   
}
	   

	