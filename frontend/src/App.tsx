import React from 'react';
import mockSlices from './mocks/mockOutput.json';
import { mockProgram } from './mocks/output';
import './App.css';
import SwitchList from './components/Switch';
import ProgramSlice from './components/ProgramSlice';
import CodeEditor from './components/CodeEditor';

// const mockSlices = [
//   {
//     name: "int y",
//     program:
//       `int[] y = new int[5];
// for (int i = 0; i < 5; i++) {
//   y[i] = i*2;
// }`,
//   },
//   {
//     name: "int z",
//     program:
//       `int[] z = new int[5];
// for (int i = 0; i < 5; i++) {
// z[i] = i*2;
// }`,
//   },
//   {
//     name: "int x",
//     program:
//       `int[] x = new int[5];
// for (int i = 0; i < 5; i++) {
// x[i] = i*2;
// }`,
//   }
// ]

function App() {
  const [slices, setShowSlices] = React.useState(mockSlices.map(e => ({
    ...e,
    show: true,
  })));

  const toggleShowSlice = (name: string) => {
    setShowSlices(slices.map(e => {
      return e.name === name ? {
        ...e,
        show: !e.show
      } : e;
    }));
  }

  return (
    <div className="App" style={{ margin: 40, display: "flex", justifyContent: "space-between" }}>
      <div style={{ marginRight: 20 }}>
        <SwitchList slices={slices} toggleShowSlice={toggleShowSlice} />
        <CodeEditor text={mockProgram} readOnly={true} height="calc(100% - 205px - 50px)" />
      </div>
      <div style={{ width: "100%" }}>
        {slices.filter(e => e.show).map((slice, idx) => {
          console.log(slice)
          return (
            <ProgramSlice
              name={slice.name}
              output={slice}
              marginBottom={idx === slices.length - 1 ? "0" : "20px"}
            />
          )
        })}
      </div>
    </div>
  )
}

export default App;
