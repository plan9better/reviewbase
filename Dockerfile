FROM debian:latest
RUN apt update && apt install -y git python3 python3-pip python3-venv && apt clean
RUN useradd -ms /bin/bash appuser
USER appuser
WORKDIR /home/appuser/app
RUN git clone https://github.com/plan9better/reviewbase_model .
RUN python3 -m venv venv
RUN mkdir models
RUN /bin/bash -c "source venv/bin/activate && pip install -r requirements.txt"
EXPOSE 13337
CMD ["/bin/bash", "-c", "source venv/bin/activate && python main.py && python server.py"]
