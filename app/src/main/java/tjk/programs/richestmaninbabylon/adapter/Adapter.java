package tjk.programs.richestmaninbabylon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tjk.programs.richestmaninbabylon.R;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    private List<String> data;

    public Adapter(Context context, List<String> data) {
        this.layoutInflater = LayoutInflater.from(context);
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.item,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String title = data.get(position);
        holder.title.setText(title);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, description;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
//
//            Intent i = new Intent(itemView.getContext(), DetailAudioActivity.class);
////            i.putExtra("title", data.get(getAdapterPosition()));
//            itemView.getContext().startActivity(i);

            title = itemView.findViewById(R.id.nameChapter);
            description = itemView.findViewById(R.id.descChapter);
        }
    }
}
