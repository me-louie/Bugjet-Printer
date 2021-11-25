import React from 'react';
import SwitchList from '../components/Switch';
import ProgramSlice from '../components/ProgramSlice';
import CodeEditor from '../components/CodeEditor';
import { IMarker } from 'react-ace';
import { Output } from '../mocks/output';

interface Props {
  program: string;
  output: Output[];
}

function OutputView(props: Props) {
  const { program, output } = props;

  const [slices, setShowSlices] = React.useState(output.map(e => ({
    ...e,
    show: true,
  })));
  const [marker, setMarker] = React.useState<IMarker[]>([]);

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
      <div style={{ marginRight: 20, height: 'calc(100vh - 80px)', minHeight: 600 }}>
        <SwitchList slices={slices} toggleShowSlice={toggleShowSlice} />
        <CodeEditor
          text={program}
          readOnly={true}
          height="calc(100% - 205px - 20px)"
          markers={marker}
        />
      </div>
      <div style={{ width: "100%" }}>
        {slices.filter(e => e.show).map((slice, idx) => {
          return (
            <ProgramSlice
              name={slice.name}
              output={slice}
              marginBottom={idx === slices.length - 1 ? "0" : "20px"}
              setMarker={setMarker}
            />
          )
        })}
      </div>
    </div>
  )
}

export default OutputView;
