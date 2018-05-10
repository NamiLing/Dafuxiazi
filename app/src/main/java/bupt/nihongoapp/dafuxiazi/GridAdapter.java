package bupt.nihongoapp.dafuxiazi;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import bupt.nihongoapp.dafuxiazi.R;

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.GridHolder>{

    private String TAG = "Acivtity_KanaGrid";
    private String[] mkana;
    private LayoutInflater mInflater;
    private KanaGrid.OnItemClickListener mOnItemClickListener;

    // data is passed into constructor
    public GridAdapter(Context context, String[] kana) {
        mInflater = LayoutInflater.from(context);
        mkana = kana;
    }

    // inflates the cell layout from xml when needed
    @Override
    public GridHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /**
         * 第三步，得到item的布局，然后为其设置OnClickListener监听器
         */
        View view = mInflater.inflate(R.layout.item_simpletext, parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * 第五步，使用getTag方法获取点击的item的position
                 */
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(view, (int) view.getTag());
                }
            }
        });
        return new GridHolder(view);
    }

    // binds the data to the textview in each cell
    @Override
    public void onBindViewHolder(GridHolder holder, int position) {
        /**
         * 第四步，将position保存在itemView的Tag中，以便点击时进行获取
         */
        holder.itemView.setTag(position);
        holder.tv_kana.setText(mkana[position]);

    }

    @Override
    public int getItemCount() {
        return mkana.length;
    }

    /**
     * 第二步，为Activity提供设置OnItemClickListener的接口
     *
     * @param listener
     */
    public void setOnItemClickListener(KanaGrid.OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    /**
     * View Holder类
     */
    public class GridHolder extends RecyclerView.ViewHolder{
        TextView tv_kana;

        public GridHolder(View itemView) {
            super(itemView);
            this.tv_kana = itemView.findViewById(R.id.item_kana);
        }

    }
}


