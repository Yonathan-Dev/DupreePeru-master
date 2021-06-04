package com.dupreincaperu.dupree.mh_holders;

import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.dupreeinca.lib_api_rest.model.dto.response.ItemPanelGte;
import com.dupreincaperu.dupree.databinding.ItemCircleprogressBinding;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;

/**
 * Created by marwuinh@gmail.com on 2/27/19.
 */

public class PanelGteHolder extends RecyclerView.ViewHolder{
    private ItemCircleprogressBinding binding;
    private Events events;

    public PanelGteHolder(@NonNull ItemCircleprogressBinding binding, Events events) {
        super(binding.getRoot());
        this.binding = binding;
        this.events = events;
    }

    public void bindData(final ItemPanelGte item) {
        try{
            binding.tvNameCamp.setText(item.getCampana());
            binding.tvMeta.setText("META");
            binding.tvValor.setText("EJECUTADO");

            Line line;
            List<PointValue> values;

            LineChartData data;

            List<Line> lines = new ArrayList<Line>();
            // ***************PRIMA LINEA***************************************
            values = new ArrayList<PointValue>();
            if(!item.getValo_camp1().trim().isEmpty() && item.getValo_camp1() != null && item.getValo_camp1() != "NAN"){
                values.add(new PointValue(0, Integer.parseInt(item.getValo_camp1())));
            }else{
                values.add(new PointValue(0, 0));
            }

            if(!item.getValo_camp2().trim().isEmpty() && item.getValo_camp2() != null && item.getValo_camp2() != "NAN"){
                values.add(new PointValue(1, Integer.parseInt(item.getValo_camp2())));
            }else{
                values.add(new PointValue(1, 0));
            }

            if(!item.getValo_camp3().trim().isEmpty() && item.getValo_camp3() != null && item.getValo_camp3() != "NAN"){
                values.add(new PointValue(2, Integer.parseInt(item.getValo_camp3())));
            }else{
                values.add(new PointValue(2, 0));
            }

            line = new Line(values).setCubic(false).setHasPoints(true).setHasLabels(true)
                    .setStrokeWidth(1);

            line.setColor(Color.parseColor("#008bfc")).setCubic(false);
            line.setStrokeWidth(1);
            line.setPointRadius(0);


            lines.add(line);

            // SEGUNDA LINEA
            values = new ArrayList<PointValue>();
            if(!item.getValo_meta1().trim().isEmpty() && item.getValo_meta1() != null && item.getValo_meta1() != "NAN"){
                values.add(new PointValue(0, Integer.parseInt(item.getValo_meta1())));
            }else{
                values.add(new PointValue(0, 0));
            }

            if(!item.getValo_meta2().trim().isEmpty() && item.getValo_meta2() != null && item.getValo_meta2() != "NAN"){
                values.add(new PointValue(1, Integer.parseInt(item.getValo_meta2())));
            }else{
                values.add(new PointValue(1, 0));
            }

            if(!item.getValo_meta3().trim().isEmpty() && item.getValo_meta3() != null && item.getValo_meta3() != "NAN"){
                values.add(new PointValue(2, Integer.parseInt(item.getValo_meta3())));
            }else{
                values.add(new PointValue(2, 0));
            }

            line = new Line(values).setCubic(false)
                    .setStrokeWidth(1).setHasPoints(true).setHasLabels(true);

            line.setColor(Color.parseColor("#00C853")).setCubic(false);
            line.setStrokeWidth(1);
            line.setPointRadius(0);

            lines.add(line);

            data = new LineChartData(lines);

            // asseX sotto
            List<AxisValue> axisValues = new ArrayList<AxisValue>();
            String[] axisData2 = {item.getValo_desc1(),item.getValo_desc2(),item.getValo_desc3()};

            Axis tempoAxis = new Axis(axisValues).setMaxLabelChars(10000)
                    .setHasLines(true).setLineColor(Color.BLACK)
                    .setTextColor(Color.BLACK)
                    .setHasSeparationLine(true).setTextSize(7);
            data.setAxisXBottom(tempoAxis);


            data.setAxisYLeft(new Axis().setTextColor(Color.BLUE).setHasLines(true)
                    .setLineColor(Color.BLACK).setTextSize(7).setName("Valor"));

            String[] axisData = {item.getValo_desc1(),item.getValo_desc2(),item.getValo_desc3()};
            axisValues = new ArrayList<AxisValue>();
            for (int i = 0; i < 3; i ++) {
                axisValues.add(new AxisValue(i).setLabel((axisData[i])));
            }
            Axis axis = new Axis();
            axis.setValues(axisValues).setHasLines(true).setMaxLabelChars(2)
                    .setHasSeparationLine(true);
            data.setAxisXBottom(axis);

            data.setAxisYRight(tempoAxis);

            binding.chart.setLineChartData(data);

            final Viewport v = new Viewport(binding.chart.getMaximumViewport());
            v.left = 0;
            v.right = (float) 2.5;
            binding.chart.setMaximumViewport(v);
            binding.chart.setCurrentViewport(v);


            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(events != null)
                        events.onClickRoot(item, getAdapterPosition());
                }
            });

        }catch (Exception ex){

        }
    }

    public interface Events{
        void onClickRoot(ItemPanelGte dataRow, int row);
    }
}
