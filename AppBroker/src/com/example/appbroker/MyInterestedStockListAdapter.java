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
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MyInterestedStockListAdapter extends ArrayAdapter<StocksHeader> {
	public Context context;
	private ArrayList<StocksHeader> values;
	private LayoutInflater inflater;
	Activity parentActivity;
	public int rowId;
	public MyInterestedStockListAdapter(Context context, ArrayList<StocksHeader> values,Activity parentActivity) {
		super(context, R.layout.list_item_my_interesteds_stock, values);
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
			rowView = inflater.inflate(R.layout.list_item_my_interesteds_stock, parent,
					false);
		}
		StocksHeader sh = values.get(position);
		TextView textView = (TextView) rowView
				.findViewById(R.id.name_my_interest);
		TextView textView2 = (TextView) rowView
				.findViewById(R.id.code_my_interest);
		TextView textViewChange = (TextView) rowView
				.findViewById(R.id.change_my_interest);
		TextView textViewValue = (TextView) rowView
				.findViewById(R.id.val_my_interest);
		ImageView remove = (ImageView) rowView
				.findViewById(R.id.remove_my_interest);

		textView.setText(sh.getStrAd());
		textView2.setText(sh.getStrKod());
		textViewChange.setText("" +  sh.getDblYuzdeDegisimGunluk());
		textViewValue.setText("" +  sh.getDblSon());
		
		if (sh.getDblYuzdeDegisimGunluk() > 0) {
			textViewChange.setTextColor(Color.parseColor("#16CC26"));
		} else if (sh.getDblYuzdeDegisimGunluk() < 0) {
			textViewChange.setTextColor(Color.parseColor("#E81010"));
		} else {
			textViewChange.setTextColor(Color.parseColor("#EDA41C"));
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
								StocksHeader s ;
								s = DisplayMyInteresteds.stock_list.get(position);
								DisplayMyInteresteds.removeDb(s.strKod);
								DisplayMyInteresteds.stock_list.remove(s);
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

	
}