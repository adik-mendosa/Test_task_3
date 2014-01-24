import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;


public class Context_Search {
	String current_dir = new File(".").getAbsolutePath();
	JFileChooser chooser = new JFileChooser(current_dir);
	int returnVal;
	String file_content="";
	String [] temp;
	String [] file_strings;
	List <String> file_values = new ArrayList <String> ();
	List <String> file_words = new ArrayList <String> ();
	String regex="^";
	int j,k=0;
	String [] words_file;
	String [] words_sentence;
	int count=0;
	int max;
	List <Integer> word_max = new ArrayList <Integer> ();
	List <Integer> result = new ArrayList <Integer> ();
	int temp_res;
	int string_sim;
	int result_num;
	int minus;
	FileNameExtensionFilter filter = new FileNameExtensionFilter("text files","txt");
	public void search(String sentence)
	{	
		chooser.setFileFilter(filter);
		System.out.println("Select dictionary file"); // opening file with dictionary
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		returnVal = chooser.showOpenDialog(chooser);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			System.out.println(chooser.getSelectedFile().getName());
		
			
			try {
				FileInputStream input_stream = new FileInputStream(chooser.getSelectedFile());   // reading data from file
				int data = input_stream.available();
				for (int i=0; i < data; i++) {	
					file_content+=(char)input_stream.read();
				}
				
				
				file_content=new String(file_content.getBytes("ISO-8859-1"),"cp1251"); // encode so russian symbols were correct.
				input_stream.close();
				sentence=sentence.toLowerCase(); // format to lower case so that comparsion was case insensitive 
				file_strings=file_content.split("\\n");
				
				

				// loop all file data separates into 2 arraylists one consists only sentence and second consists only value
				for (int i=0; i<file_strings.length;i++)
				{
					if(i==file_strings.length-1)
					{
						file_words.add(file_strings[i].substring(file_strings[i].indexOf('=')+1,file_strings[i].length()).toLowerCase());  
						file_values.add(file_strings[i].substring(0,file_strings[i].indexOf('='))); 
						continue;
					}	
					file_words.add(file_strings[i].substring(file_strings[i].indexOf('=')+1,file_strings[i].length()-1).toLowerCase());
					file_values.add(file_strings[i].substring(0,file_strings[i].indexOf('=')));
				}

		
				for (int i=0; i<file_words.size();i++) //main loop
				{
					/*	temp=file_words.get(i).split("\\s+");

					for(int j=0;j<temp.length;j++)	
					{
						regex+="(?=.*" + temp[j]+")";      // without advanced, using regex
						if (j==temp.length-1)
							regex+=".*$";	
					}
					if(sentence.matches(regex))
					{
						System.out.println(file_values.get(i));
					}	
					regex=""; */
					
			
					words_file=file_words.get(i).split("\\s+"); // the advanced task was to compare strings with words that can be similar 
					words_sentence=sentence.split("\\s");	  //and words can be swapped, so i split sentences into words, so i could compare
															  // not strings, but all words in one string with all words in another string  
				
				
					for (int k=0;k<words_file.length;k++) //in this loop I compare all words from one string with all words from other string
					{									 //and i save max. value of similarity of one word with another word no matter where 	
														//these words are positioned
						for(int z=0;z<words_sentence.length;z++)
						{
							while(count<words_file[k].length() && count<words_sentence[z].length())

							{													
								if(words_file[k].charAt(count)==words_sentence[z].charAt(count))	
								{
									max++;	
								}
								count++;
							}
							if(z==0)
								word_max.add(max);
							else if (word_max.get(k)<max)
								word_max.set(k,max);
							count=0;
							max=0;
						}
					}
					
					
					for(int j=0;j<word_max.size();j++)// in this loop I put to result arr.l. sum of all max words similarity with other words 
					{								 //  and clear word_max arr. to repeat loop again with another sentence in dictionary.
						temp_res+=word_max.get(j);	
					}	
					result.add(temp_res);
					temp_res=0;
					word_max.clear();
				}
				
				
				for(int i=0;i<result.size();i++) // in this loop i get maximum result of comparison among all sentences in dictionary.
				{								//  and number of sentence where similarity is maximum.
					if(i==0)	
					{
						string_sim=result.get(i);	
						result_num=i;
					}
					else
						if
						(string_sim<result.get(i))
						{	
							string_sim=result.get(i);	
							result_num=i;
						}
				}
				
				
				string_sim=string_sim+words_file.length-1; // i add to my string similarity value length of arr with sentence 
														  //  splitted into words, to take into account white spaces.
														 //because word were only compared 
				
				if(sentence.length()>file_words.get(result_num).length()) //if entered sentence is longer than sentence in dictionary
																		 //than entered sentence will be max value for percentage val.  
				{	
					double result=(double)string_sim/(double)sentence.length()*100; //convert string_sim to percentage
					
					if(result>50) //print out result only if strings similar more than 50 %.
						System.out.println("String similarity = " + result + " result is "+file_values.get(result_num));
					else
						System.out.println("No similar string found");	
				}
				
				else
				{	
					double result=(double)string_sim/(double)file_words.get(result_num).length()*100;
					if(result>50)
						System.out.println("String similarity = " + result + " result is "+file_values.get(result_num));
					else
						System.out.println("No similar string found");	
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Error occured while opening file");
			}
		}
	}
}
