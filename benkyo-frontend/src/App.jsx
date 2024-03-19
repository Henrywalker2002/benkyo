import './App.css';
import { Route, Routes} from 'react-router-dom'
import Home from './component/Home';
import List from './list/List';
import ListDetail from './list/ListDetail';
import Quiz from './quiz/Quiz';
import HardMode from './quiz/HardMode';

function App(props) {
  return (
    <Routes>
      <Route path="/" element={<Home />} />
      <Route path="/list" element={<List />} />
      <Route path="/list/:codeName" element={<ListDetail />} />
      <Route path="/list/:codename/quiz" element={<Quiz />} />
      <Route path="/list/:codename/hard-quiz" element={<HardMode />} />
    </Routes>
  );
}

export default App;
