package commonUtils;

import org.apache.commons.lang.StringUtils;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;

import java.awt.image.BufferedImage;
import java.io.*;

public class BarCodeUtil {

    /**
     * 生成字节
     *
     * @param msg 消息
     * @return 生成条形码流
     */
    public static InputStream generate(String msg) {
        ByteArrayOutputStream ous = new ByteArrayOutputStream();
        generate(msg, ous, 2D, 15D, 3D);
        return new ByteArrayInputStream(ous.toByteArray());
    }

    /**
     * @param msg
     * @param width  width 1=90px
     * @param height height 1=5.9px
     * @param size   1=5px
     * @return
     */
    public static InputStream generate(String msg, double width, double height, double size) {
        ByteArrayOutputStream ous = new ByteArrayOutputStream();
        generate(msg, ous, width, height, size);
        return new ByteArrayInputStream(ous.toByteArray());
    }

    /**
     * 生成到流
     *
     * @param msg 文本
     * @param ous 输出流
     */
    private static void generate(String msg, OutputStream ous, double width, double height, double size) {
        if (StringUtils.isEmpty(msg) || ous == null) {
            return;
        }

        Code128Bean bean = new Code128Bean();

        // 精细度
        final int dpi = 150;
        // module宽度
        final double moduleWidth = UnitConv.in2mm(width / dpi);

        // 配置对象
        bean.setModuleWidth(moduleWidth);
        bean.setHeight(height);
        bean.setFontSize(size);
        bean.doQuietZone(false);

        String format = "image/png";
        try {

            // 输出到流
            BitmapCanvasProvider canvas = new BitmapCanvasProvider(ous, format, dpi,
                    BufferedImage.TYPE_BYTE_BINARY, false, 0);

            // 生成条形码
            bean.generateBarcode(canvas, msg);

            // 结束绘制
            canvas.finish();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
