// axios post 
import { Route, Routes } from 'react-router-dom';
import Login from './pages/Login';
import Mainpage from './pages/Mainpage';
import Navbar from './components/Navbar/Navbar';

function App() {
  return (
    <div className="App">

      <Navbar>
      </Navbar>
      <div>
      <Routes>
        <Route path="/" element={<Mainpage/>} />
        <Route path="/login" element={<Login/>} />
        <Route path="/*" element={<h2>not found</h2>} />
      </Routes>
      </div>
    </div>
  );
}

export default App;
