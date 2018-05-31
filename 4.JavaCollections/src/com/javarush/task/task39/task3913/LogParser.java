package com.javarush.task.task39.task3913;

import com.javarush.task.task39.task3913.query.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.javarush.task.task39.task3913.LogString.convertToDate;

public class LogParser implements IPQuery, UserQuery, DateQuery, EventQuery, QLQuery {
    private Path logDir;
    private Set<LogString> logs;

    public LogParser(Path logDir) {
        this.logDir = logDir;
        logs = getLogs();
    }

    @Override
    public int getNumberOfUniqueIPs(Date after, Date before) {
        return (int) logs.stream()
                .filter(logString -> logString.isInDate(after, before))
                .map(LogString::getIp)
                .distinct()
                .count();
    }

    @Override
    public Set<String> getUniqueIPs(Date after, Date before) {
        return logs.stream()
                .filter(logString -> logString.isInDate(after, before))
                .map(LogString::getIp)
                .distinct()
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getIPsForUser(String user, Date after, Date before) {
        return logs.stream()
                .filter(logString -> logString.isInDate(after, before))
                .filter(logString -> logString.getName().equals(user))
                .map(LogString::getIp)
                .distinct()
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getIPsForEvent(Event event, Date after, Date before) {
        return logs.stream()
                .filter(logString -> logString.isInDate(after, before))
                .filter(logString -> logString.getEvent() == event)
                .map(LogString::getIp)
                .distinct()
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getIPsForStatus(Status status, Date after, Date before) {
        return logs.stream()
                .filter(logString -> logString.isInDate(after, before))
                .filter(logString -> logString.getStatus() == status)
                .map(LogString::getIp)
                .distinct()
                .collect(Collectors.toSet());
    }

    private Set<LogString> getLogs() {
        return Arrays.stream(Objects.requireNonNull(logDir.toFile().listFiles()))
                .filter(f -> f.getName().endsWith(".log"))
                .flatMap(file -> {
                    try {
                        return Files.readAllLines(file.toPath()).stream();
                    } catch (IOException e) {
                        return new ArrayList<String>().stream();
                    }
                })
                .map(LogString::new)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getAllUsers() {
        return logs.stream()
                .map(LogString::getName)
                .distinct()
                .collect(Collectors.toSet());
    }

    @Override
    public int getNumberOfUsers(Date after, Date before) {
        return (int) logs.stream()
                .filter(logString -> logString.isInDate(after, before))
                .map(LogString::getName)
                .distinct()
                .count();
    }

    @Override
    public int getNumberOfUserEvents(String user, Date after, Date before) {
        return (int) logs.stream()
                .filter(logString -> logString.isInDate(after, before))
                .filter(logString -> logString.getName().equals(user))
                .map(LogString::getEvent)
                .distinct()
                .count();
    }

    @Override
    public Set<String> getUsersForIP(String ip, Date after, Date before) {
        return logs.stream()
                .filter(logString -> logString.isInDate(after, before))
                .filter(logString -> logString.getIp().equals(ip))
                .map(LogString::getName)
                .distinct()
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getLoggedUsers(Date after, Date before) {
        return logs.stream()
                .filter(logString -> logString.isInDate(after, before))
                .filter(logString -> logString.getEvent() == Event.LOGIN)
                .map(LogString::getName)
                .distinct()
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getDownloadedPluginUsers(Date after, Date before) {
        return logs.stream()
                .filter(logString -> logString.isInDate(after, before))
                .filter(logString -> logString.getEvent() == Event.DOWNLOAD_PLUGIN)
                .map(LogString::getName)
                .distinct()
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getWroteMessageUsers(Date after, Date before) {
        return logs.stream()
                .filter(logString -> logString.isInDate(after, before))
                .filter(logString -> logString.getEvent() == Event.WRITE_MESSAGE)
                .map(LogString::getName)
                .distinct()
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getSolvedTaskUsers(Date after, Date before) {
        return logs.stream()
                .filter(logString -> logString.isInDate(after, before))
                .filter(logString -> logString.getEvent() == Event.SOLVE_TASK)
                .map(LogString::getName)
                .distinct()
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getSolvedTaskUsers(Date after, Date before, int task) {
        return logs.stream()
                .filter(logString -> logString.isInDate(after, before))
                .filter(logString -> logString.getEvent() == Event.SOLVE_TASK)
                .filter(logString -> logString.getTask() == task)
                .map(LogString::getName)
                .distinct()
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getDoneTaskUsers(Date after, Date before) {
        return logs.stream()
                .filter(logString -> logString.isInDate(after, before))
                .filter(logString -> logString.getEvent() == Event.DONE_TASK)
                .map(LogString::getName)
                .distinct()
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getDoneTaskUsers(Date after, Date before, int task) {
        return logs.stream()
                .filter(logString -> logString.isInDate(after, before))
                .filter(logString -> logString.getEvent() == Event.DONE_TASK)
                .filter(logString -> logString.getTask() == task)
                .map(LogString::getName)
                .distinct()
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Date> getDatesForUserAndEvent(String user, Event event, Date after, Date before) {
        return logs.stream()
                .filter(logString -> logString.isInDate(after, before))
                .filter(logString -> logString.getName().equals(user))
                .filter(logString -> logString.getEvent() == event)
                .map(LogString::getDate)
                .distinct()
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Date> getDatesWhenSomethingFailed(Date after, Date before) {
        return logs.stream()
                .filter(logString -> logString.isInDate(after, before))
                .filter(logString -> logString.getStatus() == Status.FAILED)
                .map(LogString::getDate)
                .distinct()
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Date> getDatesWhenErrorHappened(Date after, Date before) {
        return logs.stream()
                .filter(logString -> logString.isInDate(after, before))
                .filter(logString -> logString.getStatus() == Status.ERROR)
                .map(LogString::getDate)
                .distinct()
                .collect(Collectors.toSet());
    }

    @Override
    public Date getDateWhenUserLoggedFirstTime(String user, Date after, Date before) {
        return logs.stream()
                .filter(logString -> logString.isInDate(after, before))
                .filter(logString -> logString.getName().equals(user))
                .filter(logString -> logString.getEvent() == Event.LOGIN)
                .map(LogString::getDate)
                .sorted()
                .findFirst().orElse(null);
    }

    @Override
    public Date getDateWhenUserSolvedTask(String user, int task, Date after, Date before) {
        return logs.stream()
                .filter(logString -> logString.isInDate(after, before))
                .filter(logString -> logString.getName().equals(user))
                .filter(logString -> logString.getEvent() == Event.SOLVE_TASK)
                .filter(logString -> logString.getTask() == task)
                .map(LogString::getDate)
                .sorted()
                .findFirst().orElse(null);
    }

    @Override
    public Date getDateWhenUserDoneTask(String user, int task, Date after, Date before) {
        return logs.stream()
                .filter(logString -> logString.isInDate(after, before))
                .filter(logString -> logString.getName().equals(user))
                .filter(logString -> logString.getEvent() == Event.DONE_TASK)
                .filter(logString -> logString.getTask() == task)
                .map(LogString::getDate)
                .sorted()
                .findFirst().orElse(null);
    }

    @Override
    public Set<Date> getDatesWhenUserWroteMessage(String user, Date after, Date before) {
        return logs.stream()
                .filter(logString -> logString.isInDate(after, before))
                .filter(logString -> logString.getName().equals(user))
                .filter(logString -> logString.getEvent() == Event.WRITE_MESSAGE)
                .map(LogString::getDate)
                .sorted()
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Date> getDatesWhenUserDownloadedPlugin(String user, Date after, Date before) {
        return logs.stream()
                .filter(logString -> logString.isInDate(after, before))
                .filter(logString -> logString.getName().equals(user))
                .filter(logString -> logString.getEvent() == Event.DOWNLOAD_PLUGIN)
                .map(LogString::getDate)
                .sorted()
                .collect(Collectors.toSet());
    }

    @Override
    public int getNumberOfAllEvents(Date after, Date before) {
        return (int) logs.stream()
                .filter(logString -> logString.isInDate(after, before))
                .map(LogString::getEvent)
                .distinct()
                .count();
    }

    @Override
    public Set<Event> getAllEvents(Date after, Date before) {
        return logs.stream()
                .filter(logString -> logString.isInDate(after, before))
                .map(LogString::getEvent)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Event> getEventsForIP(String ip, Date after, Date before) {
        return logs.stream()
                .filter(logString -> logString.isInDate(after, before))
                .filter(logString -> logString.getIp().equals(ip))
                .map(LogString::getEvent)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Event> getEventsForUser(String user, Date after, Date before) {
        return logs.stream()
                .filter(logString -> logString.isInDate(after, before))
                .filter(logString -> logString.getName().equals(user))
                .map(LogString::getEvent)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Event> getFailedEvents(Date after, Date before) {
        return logs.stream()
                .filter(logString -> logString.isInDate(after, before))
                .filter(logString -> logString.getStatus() == Status.FAILED)
                .map(LogString::getEvent)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Event> getErrorEvents(Date after, Date before) {
        return logs.stream()
                .filter(logString -> logString.isInDate(after, before))
                .filter(logString -> logString.getStatus() == Status.ERROR)
                .map(LogString::getEvent)
                .collect(Collectors.toSet());
    }

    @Override
    public int getNumberOfAttemptToSolveTask(int task, Date after, Date before) {
        return (int) logs.stream()
                .filter(logString -> logString.isInDate(after, before))
                .filter(logString -> logString.getEvent() == Event.SOLVE_TASK)
                .filter(logString -> logString.getTask() == task)
                .count();
    }

    @Override
    public int getNumberOfSuccessfulAttemptToSolveTask(int task, Date after, Date before) {
        return (int) logs.stream()
                .filter(logString -> logString.isInDate(after, before))
                .filter(logString -> logString.getEvent() == Event.DONE_TASK)
                .filter(logString -> logString.getTask() == task)
                .count();
    }

    @Override
    public Map<Integer, Integer> getAllSolvedTasksAndTheirNumber(Date after, Date before) {
        return logs.stream()
                .filter(logString -> logString.isInDate(after, before))
                .filter(logString -> logString.getEvent() == Event.SOLVE_TASK)
                .map(LogString::getTask)
                .distinct()
                .collect(Collectors.toMap(task -> task,
                        task -> (int) logs.stream()
                                .filter(logString -> logString.isInDate(after, before))
                                .filter(logString -> logString.getEvent() == Event.SOLVE_TASK)
                                .filter(logString -> logString.getTask() == task)
                                .count()));
    }

    @Override
    public Map<Integer, Integer> getAllDoneTasksAndTheirNumber(Date after, Date before) {
        return logs.stream()
                .filter(logString -> logString.isInDate(after, before))
                .filter(logString -> logString.getEvent() == Event.DONE_TASK)
                .map(LogString::getTask)
                .distinct()
                .collect(Collectors.toMap(task -> task,
                        task -> (int) logs.stream()
                                .filter(logString -> logString.isInDate(after, before))
                                .filter(logString -> logString.getEvent() == Event.DONE_TASK)
                                .filter(logString -> logString.getTask() == task)
                                .count()));
    }

    @Override
    public Set<Object> execute(String query) {
        String[] dateSplit = query.split(" and");
        Set<Object> result = null;

        String[] queryParts = dateSplit[0].split(" ");
        Get mapping = Get.valueOf(queryParts[1]);

        if (queryParts.length > 5) {
            Get filter = Get.valueOf(queryParts[3]);
            String value = (dateSplit[0].substring(dateSplit[0].indexOf(queryParts[5]))).replace("\"", "");
            Set<LogString> setAfterFilter = filter.filter(logs, value).distinct().collect(Collectors.toSet());
            if (dateSplit.length > 1) {
                Date after = convertToDate(dateSplit[1].replaceFirst(" date between \"", "").replaceFirst("\"", ""));
                Date before = convertToDate(dateSplit[2].replace("\"", ""));
                setAfterFilter = setAfterFilter.stream().filter(logString -> logString.isInDate(after, before)).distinct().collect(Collectors.toSet());
            }
            result = mapping.map(setAfterFilter).distinct().collect(Collectors.toSet());
        } else
            result = mapping.map(logs).distinct().collect(Collectors.toSet());

        return result;
    }

    private enum Get {
        ip {
            Stream<Object> map(Set<LogString> logs) {
                return logs.stream().map(LogString::getIp);
            }

            Stream<LogString> filter(Set<LogString> logs, String value) {
                return logs.stream().filter(logString -> logString.isIp(value));
            }
        },
        user {
            Stream<Object> map(Set<LogString> logs) {
                return logs.stream().map(LogString::getName);
            }

            Stream<LogString> filter(Set<LogString> logs, String value) {
                return logs.stream().filter(logString -> logString.isName(value));
            }
        },
        date {
            Stream<Object> map(Set<LogString> logs) {
                return logs.stream().map(LogString::getDate);
            }

            Stream<LogString> filter(Set<LogString> logs, String value) {
                return logs.stream().filter(logString -> logString.isDate(convertToDate(value)));
            }
        },
        event {
            Stream<Object> map(Set<LogString> logs) {
                return logs.stream().map(LogString::getEvent);
            }

            Stream<LogString> filter(Set<LogString> logs, String value) {
                return logs.stream().filter(logString -> logString.isEvent(Event.valueOf(value)));
            }
        },
        status {
            Stream<Object> map(Set<LogString> logs) {
                return logs.stream().map(LogString::getStatus);
            }

            Stream<LogString> filter(Set<LogString> logs, String value) {
                return logs.stream().filter(logString -> logString.isStatus(Status.valueOf(value)));
            }
        };

        abstract Stream<Object> map(Set<LogString> logs);

        abstract Stream<LogString> filter(Set<LogString> logs, String value);
    }
}