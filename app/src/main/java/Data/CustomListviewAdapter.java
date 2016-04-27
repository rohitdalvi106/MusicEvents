package Data;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.example.rohitdalvi.musicevents.AppController;
import com.example.rohitdalvi.musicevents.EventDetails;
import com.example.rohitdalvi.musicevents.R;

import java.util.ArrayList;

import Model.Event;


public class CustomListviewAdapter extends ArrayAdapter<Event> {

    private LayoutInflater inflater;
    private ArrayList<Event> data;
    private Activity mContext;
    private int layoutResourceId;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public CustomListviewAdapter(Activity context, int resource, ArrayList<Event> objs) {
        super(context, resource, objs);

        data = objs;
        mContext = context;
        layoutResourceId = resource;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Event getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getPosition(Event item) {
        return super.getPosition(item);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        ViewHolder viewHolder = null;

        if (row == null){

            inflater = LayoutInflater.from(mContext);
            row = inflater.inflate(layoutResourceId, parent, false);

            viewHolder = new ViewHolder();

            viewHolder.headliner = (TextView) row.findViewById(R.id.headlinerText);
            viewHolder.venue = (TextView) row.findViewById(R.id.venueText);
            viewHolder.when = (TextView) row.findViewById(R.id.whenText);
            viewHolder.where = (TextView) row.findViewById(R.id.whereText);

            row.setTag(viewHolder);

        }
        else {
            viewHolder = (ViewHolder) row.getTag();
        }

        viewHolder.event = data.get(position);

        viewHolder.headliner.setText("Artist: " + viewHolder.event.getHeadLiner());
        viewHolder.venue.setText("Venue: " + viewHolder.event.getVenueName());
        viewHolder.when.setText("When: " + viewHolder.event.getStartDate());
        viewHolder.where.setText("Where: " + viewHolder.event.getStreet() + ", "
                + viewHolder.event.getCity() + ", " + viewHolder.event.getCountry());
        viewHolder.website = viewHolder.event.getWebsite();

        final ViewHolder finalViewHolder = viewHolder;
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, EventDetails.class);
                Bundle bundle =new Bundle();
                bundle.putSerializable("eventObj", finalViewHolder.event);
                i.putExtras(bundle);
                mContext.startActivity(i);

            }
        });

        return row;
    }



    public class ViewHolder {

        Event event;
        TextView headliner;
        TextView venue;
        TextView where;
        TextView when;
        String website;

    }


}
