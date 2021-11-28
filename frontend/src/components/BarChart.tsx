import React from "react";
import {
  BarChart as ReBarChart,
  Bar,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  Legend
} from "recharts";
// import { IMarker } from 'react-ace/lib/types';
import { Output } from '../mocks/output';

// const data = [
//   {
//     name: "Page A",
//     uv: 4000,
//     pv: 2400,
//     amt: 2400
//   },]

interface Props {
  output: Output;
  index: number;
}

interface State {
  color: string;
}


function getRandomColor() {
  const r = Math.floor(Math.random() * 200);
  const g = Math.floor(Math.random() * 200);
  const b = Math.floor(Math.random() * 200);
  const color = "rgb(" + r + "," + g + "," + b + ")";

  return color;
}

export default class BarChart extends React.PureComponent<Props, State> {
  constructor(props: Props) {
    super(props);

    this.state = {
      color: getRandomColor()
    }
  }

  //   CustomTooltip = ({ active, payload }: any) => {
  //     if (active && payload && payload.length) {
  //       console.log(payload);
  //       // const line = payload[0].payload.line as number;
  //       // this.props.setMarker([{
  //       //   startRow: line - 1,
  //       //   endRow: line,
  //       //   startCol: 0,
  //       //   endCol: 0,
  //       //   className: 'replacement_marker',
  //       //   type: 'text'
  //       // }]);

  //       // return (
  //       //   <div className="custom-tooltip" style={{ backgroundColor: 'lightgrey', paddingLeft: 10, paddingRight: 10 }}>
  //       //     <p className="line-number">Line number: {line}</p>
  //       //     <p className="value">Value: {payload[1].payload.value}</p>
  //       //     <p className="enclosing-class">Class: {payload[0].payload.enclosingClass}</p>
  //       //     <p className="enclosing-method">Method: {payload[0].payload.enclosingMethod}</p>
  //       //   </div>
  //       // );
  //     }

  //     this.props.setMarker([]);
  //     return null;
  //   }

  render() {
    const entry = this.props.output.history[this.props.index].value;
    const parsedArr = JSON.parse(entry);
    console.log(parsedArr);

    // if (typeof parsedArr !== "object" || parsedArr === null) {
    //   return null;
    // } else {

    // }

    const data = typeof parsedArr !== "object" || parsedArr === null ?
      [0]
      : parsedArr.map((e: number, idx: number) => {
        return {
          index: idx,
          value: e,
          // y: e,
          // ...e,
        }
      });


    return (
      <ReBarChart
        width={500}
        height={300}
        style={{ margin: 'auto' }}
        data={data}
        margin={{

          top: 20,
          right: 30,
          left: 20,
          bottom: 5
        }}
      >
        <CartesianGrid strokeDasharray="3 3" />
        <XAxis dataKey="index" />
        <YAxis yAxisId="left" orientation="left" stroke="#8884d8" />
        <YAxis yAxisId="right" orientation="right" stroke="#82ca9d" />
        {/* <Tooltip content={this.CustomTooltip} /> */}
        <Legend />
        <Bar yAxisId="left" dataKey="value" fill={this.state.color} />
      </ReBarChart>
    );
  }
}