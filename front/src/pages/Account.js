import React, { useState, useEffect } from 'react';
import '../styles/account_page.css'
import Header from '../components/Header';
import { useLocation } from 'react-router-dom';
import Calendar from '../components/Calendar';
import InfoLessonModal from '../components/modals/InfoLessonModal';
import AccountData from '../components/AccountData';

function Account() {
    const location = useLocation()
    const userId = location.pathname.split("/")[2]
    const [currentUser, setUser] = useState({})
    const [lessonInfoModalState, setLessonInfoModalState] = useState({
        isOpen: false,
        lesson: {}
    })
    const [lessons, setLessons] = useState([])

    function openLessonInfoModal(state) {
        setLessonInfoModalState({ ...lessonInfoModalState, isOpen: state })
    }

    useEffect(() => {
        fetchAccountData()
    }, [])

    useEffect(() => {
        loadLessons()
    }, [currentUser])

    function fetchAccountData() {
        fetch('http://localhost:18018/participator/' + userId, {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            }
        })
            .then(response => response.json())
            .then(data => {
                setUser(data)
            })
    }

    function loadLessons() {
        fetch('http://localhost:18018/lessons/with_user/' + currentUser.email, {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            }
        })
            .then(response => response.json())
            .then(data => {
                setLessons(data)
            })
    }

    return (
        <>
            <div className="min-h-screen">
                <Header openLessonCreationModal={null} openLessonsRequests={null} />
                <div className='filters'></div>
                <AccountData currentUser={currentUser} />
                <Calendar lessons={lessons} setLessonInfoModalState={setLessonInfoModalState} />
                {
                    lessonInfoModalState.isOpen && <InfoLessonModal closeModal={openLessonInfoModal} currentLesson={lessonInfoModalState.lesson} />
                }
            </div >
        </>
    );
};

export default Account;
