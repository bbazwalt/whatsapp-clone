import React from "react";
import { AiOutlineClose } from "react-icons/ai";

const SelectedMember = ({ handleRemoveMember, member }) => {
  return (
    <div className="flex items-center bg-slate-300 rounded-full">
      <img
        className="w-7 h-7 rounded-full"
        src={member.profilePicture}
        alt=""
      />
      <p className="px-2"> {member.fullName} </p>
      <AiOutlineClose
        onClick={() => handleRemoveMember(member)}
        className="pr-1 cursor-pointer"
      />
    </div>
  );
};

export default SelectedMember;
