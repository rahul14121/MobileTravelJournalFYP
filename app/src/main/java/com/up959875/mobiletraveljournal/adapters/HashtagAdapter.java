package com.up959875.mobiletraveljournal.adapters;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.up959875.mobiletraveljournal.R;
import java.util.ArrayList;
import java.util.List;

//Function to display tags
public class HashtagAdapter extends ArrayAdapter<String> {

    private List<String> hashtags;

    //Constructor for the class
    public HashtagAdapter(@NonNull Context context, @NonNull List<String> tags) {
        super(context, 0, tags);
        hashtags = new ArrayList<>(tags);
    }

    @NonNull
    @Override
    //Get the filter for the tags
    public Filter getFilter() {
        return hashtagFilter;
    }


    @NonNull
    @Override
    //Get the view for the tags, and find the textview associated with the list.
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        TextView textViewName = convertView.findViewById(R.id.list_item_name);
        String item = getItem(position);
        if (item != null) {
            textViewName.setText(item);
        }

        return convertView;
    }


    // This is the filter for the tags. It is filtering the tags based on the input from the user.
    private final Filter hashtagFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<String> suggestions = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                suggestions.addAll(hashtags);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (String item : hashtags) {
                    if (item.toLowerCase().contains(filterPattern)) {
                        suggestions.add(item);
                    }
                }
            }
            results.values = suggestions;
            results.count = suggestions.size();

            return results;
        }


        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            addAll((List) results.values);
            notifyDataSetChanged();
        }


        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return "#" + resultValue;
        }

    };
}
