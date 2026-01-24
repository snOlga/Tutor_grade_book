import React, { useState } from 'react';
import '../styles/account_page.css'
import Header from '../shared/Header';
import { useLocation } from 'react-router-dom';
import Calendar from '../features/calendar/Calendar';
import InfoLessonModal from '../features/lessons/modals/InfoLessonModal';
import AccountData from '../features/account/AccountData';
import ChatHolder from '../features/chat/ChatHolder';
import { useUser } from '../hooks/useUser';
import { useLessons } from '../hooks/useLessons';

function Account() {
    const [isChatOpen, openChat] = useState(false)
    const location = useLocation()
    const userId = location.pathname.split("/")[2]
    const [lessonInfoModalState, setLessonInfoModalState] = useState({
        isOpen: false,
        lesson: {}
    })
    const [chat, setChat] = useState(null)
    const [startWeekDate, setStartWeekDate] = useState(new Date())
    const [endWeekDate, setEndWeekDate] = useState(new Date())
    const setWeek = { setStartWeekDate, setEndWeekDate }
    const { user: currentUser } = useUser(null, userId)
    const { lessons } = useLessons(startWeekDate, endWeekDate, currentUser.email)

    function openLessonInfoModal(state) {
        setLessonInfoModalState({ ...lessonInfoModalState, isOpen: state })
    }

    return (
        <>
            <div className="min-h-screen">
                <Header openLessonCreationModal={null} openLessonsRequests={null} openChat={openChat} />
                <div style={{ marginTop: "70px" }}></div>
                <AccountData currentUser={currentUser} openChatWithId={setChat} />
                <Calendar lessons={lessons} setLessonInfoModalState={setLessonInfoModalState} setWeek={setWeek} />
                {
                    lessonInfoModalState.isOpen && <InfoLessonModal closeModal={openLessonInfoModal} currentLesson={lessonInfoModalState.lesson} />
                }
                {
                    (isChatOpen || chat != null) && <ChatHolder openChat={openChat} chat={chat} setChat={setChat} />
                }
            </div >
        </>
    );
};

export default Account;
