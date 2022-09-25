import { useState } from "react";
import { FiMenu } from "react-icons/fi";
import { CgClose } from "react-icons/cg";
import WilLogo from "../static/img/WILlogo.png";
import "./Navbar.css";

const Menubar = ({ user }) => {
  const [isNavOpen, setIsNavOpen] = useState(false);

  const toggleNav = () => {
    setIsNavOpen(!isNavOpen);
  };

  const handleOnClick = (e) => {
    localStorage.removeItem("token");
    localStorage.removeItem("expiredTime");
    user = false;
  };

  return (
    <div>
      {user ? (
        <nav className="nav-bar">
          <div className="nav-container">
            <div className="logo">
              <a href="/">
                <img src={WilLogo} alt="erro img" />
              </a>
            </div>
            <ul className={isNavOpen ? "nav-links open" : "nav-links"}>
              <a href="/" className="nav-link">
                Home
              </a>
              <a href="/mypage" className="nav-link">
                MyPage
              </a>
              <a href="/" className="nav-link" onClick={handleOnClick}>
                Logout
              </a>
            </ul>
            <FiMenu
              className={isNavOpen ? "burger inactive" : "burger"}
              onClick={toggleNav}
            />
            <CgClose
              className={isNavOpen ? "close" : "close inactive"}
              onClick={toggleNav}
            />
          </div>
        </nav>
      ) : (
        <nav className="nav-bar">
          <div className="nav-container">
            <div className="logo">
              <a href="/">
                <img src={WilLogo} alt="erro img" />
              </a>
            </div>
            <ul className={isNavOpen ? "nav-links open" : "nav-links"}>
              <a href="/" className="nav-link">
                Home
              </a>
              <a href="/loginsignup" className="nav-link">
                Login
              </a>
            </ul>
            <FiMenu
              className={isNavOpen ? "burger inactive" : "burger"}
              onClick={toggleNav}
            />
            <CgClose
              className={isNavOpen ? "close" : "close inactive"}
              onClick={toggleNav}
            />
          </div>
        </nav>
      )}
    </div>
  );
};

export default Menubar;