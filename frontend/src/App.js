// axios post 
import { Route, Routes } from 'react-router-dom';
import Login from './pages/Login';
import Mainpage from './pages/Mainpage';
import Navbar from './components/Navbar/Navbar';
import OAuthLogin from './pages/OAuthLogin';
import Write from './pages/Write';
function App() {
  return (
    <div className="App">

      <Navbar>
      </Navbar>
      <div>
      <Routes>
        <Route path="/" element={<Mainpage/>} />
        <Route path="/login" element={<Login/>} />
        <Route path="/oauth2/redirect" element={<OAuthLogin/>}/>
        <Route path="/write" element={<Write/>}/>
        <Route path="/*" element={<h2>not found</h2>} />
      </Routes>
      </div>
    </div>
  );
}

export default App;
