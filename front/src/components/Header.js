import React, { useState } from 'react';
import '../styles/header_style.css'

function Header({ openChat, openLessonCreationModal }) {

    function openChatFromHeader() {
        openChat(true)
    }

    function openLessonCreation() {
        openLessonCreationModal(true)
    }

    return (
        <div className='header'>
            <a href='/' className='logo'>Gradebook</a>
            <div className='right-section'>
                <button onClick={openLessonCreationModal}>Create Lesson</button>
                {/* <button onClick={openChatFromHeader}>chat</button> */}
            </div>
        </div>
    );
};

export default Header;
