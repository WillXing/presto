/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.facebook.presto.operator.aggregation.state;

import com.facebook.presto.spi.block.Block;
import com.facebook.presto.spi.block.BlockBuilder;
import com.facebook.presto.spi.type.Type;

public class SliceStateSerializer
        implements AccumulatorStateSerializer<SliceState>
{
    private final Type type;

    public SliceStateSerializer(Type type)
    {
        this.type = type;
    }

    @Override
    public Type getSerializedType()
    {
        return type;
    }

    @Override
    public void serialize(SliceState state, BlockBuilder out)
    {
        if (state.getSlice() == null) {
            out.appendNull();
        }
        else {
            type.writeSlice(out, state.getSlice());
        }
    }

    @Override
    public void deserialize(Block block, int index, SliceState state)
    {
        state.setSlice(type.getSlice(block, index));
    }
}
