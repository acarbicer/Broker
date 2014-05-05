/* LayoutInflator:	When we design using XML, all of our UI elements are just tags and
 * 					parameters. Before we can use these UI elements, (e.g., a TextView
 * 					or LinearLayout), we need to create the actual objects corresponding
 * 					to these xml elements. That is what the inflater is for. The inflater,
 * 					uses these tags and their corresponding parameters to create the actual
 * 					objects and set all the parameters. After this one can get a reference 
 * 					to the UI element using findViewById().
 * getView():		getView() is called every time an item in the list is drawn. Now, before
 * 					the item can be drawn, it has to be created. convertView basically is the
 * 					last used view to draw an item. In getView() we inflate the xml first and
 * 					then use findByViewID() to get the various UI elements of the Listitem.
 * 					When we check for (convertView == null) what we do is check that if a 
 * 					view is null(for the first item) then create it, else, if it already 
 * 					exists, reuse it, no need to go through the inflate process again. 
 * 					It makes it a lot more efficient.
 * parent:			The parent is a ViewGroup to which your view created by getView() is 
 * 					finally attached. In our case this would be the ListView.
 */
package com.example.appbroker;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MyInterestedtListAdapter extends ArrayAdapter<Currency> {
	public Context context;
	private ArrayList<Currency> values;
	private LayoutInflater inflater;
	Activity parentActivity;
	public int rowId;
	public MyInterestedtListAdapter(Context context, ArrayList<Currency> values,Activity parentActivity) {
		super(context, R.layout.list_item_my_interesteds, values);
		this.context = context;
		this.values = values;
		this.parentActivity = parentActivity;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		if (rowView == null) {
			inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rowView = inflater.inflate(R.layout.list_item_my_interesteds, parent,
					false);
		}
		Currency c = values.get(position);

		ImageView logo = (ImageView) rowView.findViewById(R.id.logo_my_interested);
		TextView textView = (TextView) rowView
				.findViewById(R.id.name_my_interested);
		TextView textView2 = (TextView) rowView
				.findViewById(R.id.sell_my_interested);
		ImageView arrow = (ImageView) rowView
				.findViewById(R.id.increase__my_interested);
		ImageView remove = (ImageView) rowView
				.findViewById(R.id.remove_my_interested);

		textView.setText(c.getCode());
		textView2.setText("" + c.getSell());

		// Change increasing based on arrow image
				if (c.getWay().equals("Y")) {
					arrow.setImageResource(R.drawable.up_arrow);
				} else if (c.getWay().equals("A")){
					arrow.setImageResource(R.drawable.down_arrow);
				} else {
					//arrow.setImageResource(R.drawable.stable);
				}

		// Change icon based on name
				if(c.getCode().equals("TRL")){
					logo.setImageResource(R.drawable.tr);
				} else if(c.getCode().equals("CAD")){
					logo.setImageResource(R.drawable.cad);
				} else if(c.getCode().equals("AUD")){
					logo.setImageResource(R.drawable.aud);
				}else if(c.getCode().equals("CHF")){
					logo.setImageResource(R.drawable.chf);
				} else if(c.getCode().equals("DKK")){
					logo.setImageResource(R.drawable.dkk);
				} else if(c.getCode().equals("EUR")){
					logo.setImageResource(R.drawable.eur);
				} else if(c.getCode().equals("GBP")){
					logo.setImageResource(R.drawable.gbp);
				} else if(c.getCode().equals("JPY")){
					logo.setImageResource(R.drawable.jpy);
				} else if(c.getCode().equals("KWD")){
					logo.setImageResource(R.drawable.kwd);
				} else if(c.getCode().equals("NOK")){
					logo.setImageResource(R.drawable.nok);
				} else if(c.getCode().equals("SAR")){
					logo.setImageResource(R.drawable.sar);
				} else if(c.getCode().equals("SEK")){
					logo.setImageResource(R.drawable.sek);
				} else if(c.getCode().equals("USD")){
					logo.setImageResource(R.drawable.usd);
				} 
		remove.setImageResource(R.drawable.remove);

		remove.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				String msg = "Your selected item will be deleted";

				AlertDialog.Builder myDialogBox = new AlertDialog.Builder(
						parentActivity);

				// set message, title, and icon
				myDialogBox.setTitle("Unfollow Item");
				myDialogBox.setMessage(msg);
				myDialogBox.setIcon(R.drawable.remove);

				// Set three option buttons
				myDialogBox.setPositiveButton("DELETE",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								Currency c ;
								c = DisplayMyInteresteds.currency_list.get(position);
								calculateRowId(c);
								DisplayMyInteresteds.updateDB(c.getCode(),"N");
								DisplayMyInteresteds.currency_list
								.remove(c);
								Toast.makeText(getContext(), c.getCode() + " is Removed", Toast.LENGTH_SHORT)
										.show();
								//refresh listview
								notifyDataSetChanged();
							
								dialog.cancel();
							}
						});
				myDialogBox.setNegativeButton("CANCEL",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								dialog.cancel();

							}
						});

				myDialogBox.create();
				myDialogBox.show();

			}
		});

		return rowView;
	}

	
	public void calculateRowId(Currency c){
		
		if(c.getCode().toString().equals("AUD"))
		{rowId=1;}
		else if(c.getCode().toString().equals("CAD"))
		{rowId=2;}
		else if(c.getCode().toString().equals("CHF"))
		{rowId=3;}
		else if(c.getCode().toString().equals("DKK"))
		{rowId=4;}
		else if(c.getCode().toString().equals("EUR"))
		{rowId=5;}
		else if(c.getCode().toString().equals("GBP"))
		{rowId=6;}
		else if(c.getCode().toString().equals("JPY"))
		{rowId=7;}
		else if(c.getCode().toString().equals("KWD"))
		{rowId=8;}
		else if(c.getCode().toString().equals("NOK"))
		{rowId=9;}
		else if(c.getCode().toString().equals("SAR"))
		{rowId=10;}
		else if(c.getCode().toString().equals("SEK"))
		{rowId=11;}
		else if(c.getCode().toString().equals("USD"))
		{rowId=12;}
	}
}