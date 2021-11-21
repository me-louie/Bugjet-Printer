import React, { PureComponent } from 'react';
import { Output } from '../mocks/output';
import {
  ScatterChart,
  Scatter,
  XAxis,
  YAxis,
  ZAxis,
  CartesianGrid,
  Tooltip,
  Legend,
  ResponsiveContainer,
} from 'recharts';

// Import these from the JSON
// const data01 = [
//   { x: 10, y: 30 },
//   { x: 30, y: 200 },
//   { x: 45, y: 100 },
//   { x: 50, y: 400 },
//   { x: 70, y: 150 },
//   { x: 100, y: 250 },
// ];
// const data02 = [
//   { x: 30, y: 20 },
//   { x: 50, y: 180 },
//   { x: 75, y: 240 },
//   { x: 100, y: 100 },
//   { x: 120, y: 190 },
// ];

interface Props {
  output: Output;
}

// const history = mock[0].history;

// // const XAxisCount = history.length;

// // let Axisdata = [];

// const axis2 = history.map((e, idx) => {
//   return {
//     x: idx*2,
//     y: Number(e.value),
//     ...e,
//   }
// });

// console.log("Axis2: "+ axis2);


// function makeXAxis(){

//   for (let i = 0; i < XAxisCount; i++){
//     var x = { x: i*2, y: history[i].value };
//     Axisdata.push(x);
//   }

// }


const CustomTooltip = ({ active, payload, label }: any) => {
  if (active && payload && payload.length) {
    console.log(payload);
    console.log(label);
    return (
      <div className="custom-tooltip" style={{ backgroundColor: 'lightgrey', paddingLeft: 10, paddingRight: 10 }}>
        <p className="line-number">Line number: {payload[0].payload.line}</p>
        <p className="value">Value: {payload[1].payload.value}</p>
        <p className="enclosing-class">Class: {payload[0].payload.enclosingClass}</p>
        <p className="enclosing-method">Method: {payload[0].payload.enclosingMethod}</p>
      </div>
    );
  }
  return null;
}

export default class LineChart extends PureComponent<Props> {
  static demoUrl = 'https://codesandbox.io/s/scatter-chart-with-joint-line-2ucid';

  render() {
    const axis2 = this.props.output.history.map((e, idx) => {
      return {
        x: idx*2,
        y: Number(e.value),
        ...e,
      }
    });

    return (
      <ResponsiveContainer width="90%" height="100%">
        <ScatterChart
          width={500}
          height={400}
          margin={{
            top: 20,
            right: 20,
            bottom: 20,
            left: 20,
          }}
        >
          <CartesianGrid />
          <XAxis type="number" dataKey="x" name="line number"  hide/>
          {/* Maybe want to use unit as double later? */}
          <YAxis type="number" dataKey="y" name="Value" />
          <ZAxis type="number" range={[100]} />
          <Tooltip cursor={{ strokeDasharray: '3 3' }} content={<CustomTooltip />}/>
          <Legend />
          <Scatter name="double a" data={axis2} fill="#8884d8" line shape="cross" />
          {/* <Scatter name="double b" data={data02} fill="#82ca9d" line shape="diamond" /> */}
        </ScatterChart>
      </ResponsiveContainer>
    );
  }
}
