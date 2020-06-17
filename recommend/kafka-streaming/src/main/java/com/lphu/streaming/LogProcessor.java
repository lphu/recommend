package com.lphu.streaming;

import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorContext;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;
import java.util.Set;

/**
 * @author hupeilei
 * @create 2020/3/9 5:35 下午
 */
public class LogProcessor implements Processor<byte[] , byte[]> {
    private ProcessorContext context;

    @Override
    public void init(ProcessorContext processorContext) {
        this.context = processorContext;
    }

    @Override
    public void process(byte[] dummy, byte[] line) {
        // 核心处理流程
        String input = new String(line);
        // System.out.println(input);
        // 提取数据，以固定前缀过滤日志信息
        if(input.contains("PRODUCT_RATING_PREFIX:")){
            System.out.println("product rating data coming! " + input);
            input = input.split("PRODUCT_RATING_PREFIX:")[1].trim();
            System.out.println(input);
            context.forward("logProcessor".getBytes(), input.getBytes());
        }
    }

    @Override
    public void punctuate(long l) {

    }

    @Override
    public void close() {

    }
}
