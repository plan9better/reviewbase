'use client'

import { useState, useEffect } from 'react'
import ReviewList from '../../components/ReviewList'

export default function GameReviews({ params }) {
    const { gameId } = params
    const [reviews, setReviews] = useState([])
    const [user, setUser] = useState(null)

    useEffect(() => {
        // Fetch reviews
        const fetchReviews = async () => {
            const response = await fetch(`http://localhost:8080/api/v1/game/${gameId}`)
            const data = await response.json()
            setReviews(data)
        }

        // Get user info from localStorage (or another source)
        const storedUser = JSON.parse(localStorage.getItem('user') || 'null')
        setUser(storedUser)

        fetchReviews()
    }, [gameId])

    return (
        <div>
            <h2>Reviews for Game {gameId}</h2>
            <ReviewList reviews={reviews} user={user} />
        </div>
    )
}
