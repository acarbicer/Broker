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

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class StocksListAdapter extends ArrayAdapter<StocksHeader> {
	private Context context;
	private ArrayList<StocksHeader> values;
	private LayoutInflater inflater;

	public StocksListAdapter(Context context,
			ArrayList<StocksHeader> stocks_list) {
		super(context, R.layout.list_item_all_stocks, stocks_list);
		this.context = context;
		this.values = stocks_list;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		if (rowView == null) {
			inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rowView = inflater.inflate(R.layout.list_item_all_stocks, parent,
					false);
		}
		StocksHeader s = values.get(position);

		TextView textView = (TextView) rowView.findViewById(R.id.name_stocks);
		TextView textView2 = (TextView) rowView.findViewById(R.id.last_stocks);
		TextView textView3 = (TextView) rowView.findViewById(R.id.code_stocks);
		TextView textView4 = (TextView) rowView
				.findViewById(R.id.day_change_stocks);

		// CheckBox invest = (CheckBox) rowView
		// .findViewById(R.id.checkBox_stocks_invest);
		// CheckBox follow = (CheckBox) rowView
		// .findViewById(R.id.checkBox_stocks_follow);

		textView.setText(s.getStrAd());
		textView2.setText("" + s.getDblSon());
		textView3.setText("" + s.getStrKod());
		textView4.setText("" + s.getDblYuzdeDegisimGunluk() + "%");

		
		textView.setTextColor(Color.parseColor("#000000"));
		textView2.setTextColor(Color.parseColor("#000000"));
		textView3.setTextColor(Color.parseColor("#000000"));
		if (s.getDblYuzdeDegisimGunluk() > 0) {
			textView4.setTextColor(Color.parseColor("#16CC26"));
		} else if (s.getDblYuzdeDegisimGunluk() < 0) {
			textView4.setTextColor(Color.parseColor("#E81010"));
		} else {
			textView4.setTextColor(Color.parseColor("#EDA41C"));
		}
	

		return rowView;
	}
}