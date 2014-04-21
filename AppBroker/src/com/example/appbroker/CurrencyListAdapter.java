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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

public class CurrencyListAdapter extends ArrayAdapter<Currency> {
	private Context context;
	private ArrayList<Currency> values;
	private LayoutInflater inflater;
	

	public CurrencyListAdapter(Context context, ArrayList<Currency> values) {
		super(context, R.layout.list_item_all_currency, values);
		this.context = context;
		this.values = values;

	}

	
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		if (rowView == null) {
			inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rowView = inflater.inflate(R.layout.list_item_all_currency, parent,
					false);
		}
		
		final Currency c = values.get(position);

		ImageView logo = (ImageView) rowView.findViewById(R.id.logo);
		TextView textView = (TextView) rowView.findViewById(R.id.list_item1);
		TextView textView2 = (TextView) rowView.findViewById(R.id.list_item2);
		ImageView arrow = (ImageView) rowView.findViewById(R.id.increase_all);
		CheckBox invest = (CheckBox) rowView
				.findViewById(R.id.checkBox_currency_invest);
		CheckBox follow = (CheckBox) rowView
				.findViewById(R.id.checkBox_currency_follow);

		textView.setText(c.getCode());
		textView2.setText("" + c.getBuy());
		invest.setOnCheckedChangeListener(null);
		follow.setOnCheckedChangeListener(null);
		// Change increasing based on arrow image
		if (c.getWay().equals("Y")) {
			arrow.setImageResource(R.drawable.up_arrow);
		} else if (c.getWay().equals("A")) {
			arrow.setImageResource(R.drawable.down_arrow);
		} else {
			// arrow.setImageResource(R.drawable.stable);
		}

		// Change invest based on name
		if (!c.getInvest().equals("")) {
			if (c.getInvest().equals("Y")) {
				invest.setChecked(true);
			} else {
				invest.setChecked(false);
			}
		}
		// Change follow based on name
		if (!c.getFollow().equals(null)) {
			if (c.getFollow().equals("Y")) {
				follow.setChecked(true);
			} else {
				follow.setChecked(false);
			}
		}
		invest.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (buttonView.isChecked()) {
					DisplayAllInvestments.updateDB(position+1, ""+c.getName(), ""+c.getCode(), ""+c.getBuy(), ""+c.getSell(), ""+c.getEbuy(), ""+c.getEsell(), c.getWay(), c.getDate(),"Y",c.getFollow());
					DisplayAllInvestments.currency_list.get(position).setInvest("Y");
				} else {
					DisplayAllInvestments.updateDB(position+1, ""+c.getName(), ""+c.getCode(), ""+c.getBuy(), ""+c.getSell(), ""+c.getEbuy(), ""+c.getEsell(), c.getWay(), c.getDate(),"N",c.getFollow());
					DisplayAllInvestments.currency_list.get(position).setInvest("N");
				}

			}
		});
		follow.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (buttonView.isChecked()) {
					DisplayAllInvestments.updateDB(position+1, ""+c.getName(), ""+c.getCode(), ""+c.getBuy(), ""+c.getSell(), ""+c.getEbuy(), ""+c.getEsell(), c.getWay(), c.getDate(),c.getInvest(),"Y");
					DisplayAllInvestments.currency_list.get(position).setFollow("Y");
				} else {
					DisplayAllInvestments.updateDB(position+1, ""+c.getName(), ""+c.getCode(), ""+c.getBuy(), ""+c.getSell(), ""+c.getEbuy(), ""+c.getEsell(), c.getWay(), c.getDate(),c.getInvest(),"N");
					DisplayAllInvestments.currency_list.get(position).setFollow("N");
				}

			}
		});
		// Change icon based on name
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
		System.out.println(c.getCode());
		//notifyDataSetChanged();
		return rowView;
	}
}