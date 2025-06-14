import { cn } from "@/lib/utils";
import { useState, useEffect } from "react";
import { TopNavbar } from "../shared/navbar";
import SubTopNavbar from "../shared/navbar/SubTopNavbar";

interface HeaderContainerProps {
  children: React.ReactNode;
}

const Header = () => {
  const [isMobile, setIsMobile] = useState(false);

  useEffect(() => {
    const handleResize = () => {
      setIsMobile(window.innerWidth < 768); // Adjust breakpoint as needed
    };

    handleResize(); // Initial check
    window.addEventListener('resize', handleResize);

    return () => window.removeEventListener('resize', handleResize);
  }, []);

  return (
    <div className="fixed top-0 w-full [&>div]:px-page_x z-10">
      <SubTopNavbar/>
      <TopNavbar/>
    </div>
  );
};

export default Header