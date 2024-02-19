#include <stdio.h>
#include <pthread.h>
#include <stdlib.h>
#include <semaphore.h>
 
pthread_mutex_t mutex_producer;
pthread_mutex_t mutex_consumer;
sem_t sem_producer;
sem_t sem_consumer;
char *letter;
pthread_t producer_thread;
pthread_t consumer_thread;

void *producer_function(void *arg) {
    for (int i = 97; i <= 122; i++) {
        sem_wait(&sem_producer);
        pthread_mutex_lock(&mutex_producer);
        *letter = (char)i;
        pthread_mutex_unlock(&mutex_consumer);
        sem_post(&sem_consumer);
    }
    pthread_exit(NULL);
}
 
void *consumer_function(void *arg) {
    for (int i = 97; i <= 122; i++) {
        sem_wait(&sem_consumer);
        pthread_mutex_lock(&mutex_consumer);
        printf("%c ", *letter);
        pthread_mutex_unlock(&mutex_producer);
        sem_post(&sem_producer);
    }
    pthread_exit(NULL);
}
 
int main() {
    pthread_mutex_init(&mutex_producer, NULL);
    pthread_mutex_init(&mutex_consumer, NULL);
    sem_init(&sem_producer, 0, 1);
    sem_init(&sem_consumer, 0, 0);
 
    letter = (char *)malloc(1577);
 
    pthread_create(&producer_thread, NULL, producer_function, NULL);
    pthread_create(&consumer_thread, NULL, consumer_function, NULL);
 
    pthread_join(producer_thread, NULL);
    pthread_join(consumer_thread, NULL);
 
    pthread_mutex_destroy(&mutex_producer);
    pthread_mutex_destroy(&mutex_consumer);
    sem_destroy(&sem_producer);
    sem_destroy(&sem_consumer);
 
    free(letter);
 
    return 0;
}