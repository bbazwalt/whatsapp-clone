import React from "react"
import {TbCircleDashed} from "react-icons/tb"
import {BiCommentDetail} from "react-icons/bi"

const HomePage = () => {
  return (
    <div className="relative">
      <div className="w-full py-14 bg-[#00a884] ">
        <div className="flex bg-[#f0f2f5] h-[94vh] absolute top-6 left-6 w-full">
          <div className="left w-[30%] bg-[#e8e9ec] h-full">
            <div className="w-full">
              <div className="flex justify-between items-center p-3">
                <div className="flex items-center space-x-3">
                  <img
                    className="rounded-full w-10 h-10 cursor-pointer"
                    src="https://images.pexels.com/photos/14662833/pexels-photo-14662833.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"
                    alt=""
                  />
                  <p>username</p>
                </div>
                <div className="space-x-3 text-2xl flex">
                    <TbCircleDashed/>
                    <BiCommentDetail/>
                </div>
              </div>
            </div>
          </div>
          <div className="right"></div>
        </div>
      </div>
    </div>
  );
};

export default HomePage;
