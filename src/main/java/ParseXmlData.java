

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ParseXmlData {
	static final String DATASET = "Dataset";
	static final String ID = "id";
	static final String THICKNESS = "thickness";
	static final String UNIFORMITY_SIZE = "uniformity_size";
	static final String UNIFORMITY_SHAPE = "uniformity_shape";
	static final String ADHESION = "adhesion";
	static final String APITHELIAL_SIZE = "epithelial_size";
	static final String BARE_NUCEI = "bare_nuclei";
	static final String CHROMATIN = "chromatin";
	static final String NUCLEOLI = "nucleoli";
	static final String MITOSES = "mitoses";
	static final String CLASS = "CLASS";

	private ArrayList<Record> items;



	public ParseXmlData(){
		items = new ArrayList<>();


	}

	public ArrayList<Record> getItems() {
		return items;
	}

	public List<Record> parse(String xml){
		Record currentRecord = null;
		boolean status = true;
		boolean inEntry = false;
		String textValue = "";

		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			XmlPullParser xpp = factory.newPullParser();
			xpp.setInput(new StringReader(xml));

			int eventType = xpp.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT){
				String tagName = xpp.getName();
				switch (eventType){
					case XmlPullParser.START_TAG:
						Logging.getLogger().info("Starting tag: " + tagName);
						if (DATASET.equalsIgnoreCase(tagName)){
							inEntry = true;
							currentRecord = new Record();
						}
						break;
					case XmlPullParser.TEXT:
						textValue = xpp.getText();
						break;
					case XmlPullParser.END_TAG:
						Logging.getLogger().info("Ending tag: " + tagName);
						if (inEntry){
							if (DATASET.equalsIgnoreCase(tagName)){
								items.add(currentRecord);
								inEntry = false;
							} else if (ID.equalsIgnoreCase(tagName)){
								currentRecord.set_id(textValue);
							} else if (THICKNESS.equalsIgnoreCase(tagName)){
								currentRecord.set_thickness(textValue);
							} else if (UNIFORMITY_SIZE.equalsIgnoreCase(tagName)){
								currentRecord.set_uniformity_size(textValue);
							} else if (UNIFORMITY_SHAPE.equalsIgnoreCase(tagName)){
								currentRecord.set_uniformity_shape(textValue);
							} else if (ADHESION.equalsIgnoreCase(tagName)){
								currentRecord.set_adhesion(textValue);
							} else if (APITHELIAL_SIZE.equalsIgnoreCase(tagName)){
								currentRecord.set_epithelial_size(textValue);
							} else if (BARE_NUCEI.equalsIgnoreCase(tagName)){
								currentRecord.set_bare_nuclei(textValue);
							} else if (CHROMATIN.equalsIgnoreCase(tagName)){
								currentRecord.set_chromatin(textValue);
							} else if (NUCLEOLI.equalsIgnoreCase(tagName)){
								currentRecord.set_nucleoli(textValue);
							}  else if (MITOSES.equalsIgnoreCase(tagName)){
								currentRecord.set_mitoses(textValue);
							}  else if (CLASS.equalsIgnoreCase(tagName)){
								currentRecord.set_class(textValue);
							}
						}
						break;
					default:
						System.out.println("Default");
				}
				eventType =  xpp.next();
			}
		} catch (XmlPullParserException | IOException e) {
			e.printStackTrace();
		}
		return items;

	}


	public int size(){
		return this.items.size();
	}

	public void viewRecord(){
		int index = 0;
		for (var r : this.getItems()){
			System.out.println("record: " + ++index );
			System.out.println(r);
			System.out.println("");
		}
	}


}