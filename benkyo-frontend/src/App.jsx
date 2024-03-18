import './App.css';
import { Route, Routes} from 'react-router-dom'
import Home from './component/Home';
import List from './list/List';
import ListDetail from './list/ListDetail';

function App(props) {
  return (
    <Routes>
      <Route path="/" element={<Home />} />
      <Route path="/list" element={<List />} />
      <Route path="/list/:codeName" element={<ListDetail />} />
    </Routes>
  );
}

export default App;
